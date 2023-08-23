package com.example.lokaltask;


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.ui.platform.LocalContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lokaltask.API.RetrofitInstance
import com.example.lokaltask.ApiResponse.ProductResponse
import com.example.lokaltask.ApiResponse.Products
import com.example.lokaltask.adapter.ProductAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ProductAdapter.OnProductClickListener, FilterDialogFragment.FilterListener {

    private var doubleBackToExitPressedOnce = false
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var recyclerView: RecyclerView
    private var productsList: List<Products> = emptyList()
    private var originalProductsList: List<Products> = emptyList()
    private lateinit var progressBar : ProgressBar
    private var brandList: HashSet<String> = HashSet()
    private var categoryList: HashSet<String> = HashSet()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        toolbar.setLogo(R.drawable.logo)
        toolbar.title = "Lokal Products"

        recyclerView = findViewById(R.id.products_recycler_view)
        progressBar = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
//        FloatingActionButton = findViewById(R.id.fadAdd)

        recyclerView.isVerticalScrollBarEnabled = false
        recyclerView.isHorizontalScrollBarEnabled = false

//        val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//        val colorScheme = when {
//            dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
//            dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
//            darkTheme -> darkColorScheme(...)
//            else -> lightColorScheme(...)
//        }
//        MaterialTheme(
//            colorScheme = colorScheme,
//            typography = typography,
//            shapes = shapes
//        ) {
//            // M3 App content
//        }

//        brandList.add("All Brands")
//        categoryList.add("All Categories")

        swipeRefreshLayout.setOnRefreshListener {

            // Call the method that updates your data here
            fetchProducts()

            // When the refreshing is done, call this
            swipeRefreshLayout.isRefreshing = false
        }
        val filterFab = findViewById<ExtendedFloatingActionButton>(R.id.fadAdd)

        filterFab.setOnClickListener { showFilterDialog(brandList, categoryList) }

        fetchProducts()
    }

    private fun fetchProducts() {
        progressBar.visibility = View.VISIBLE // Show ProgressBar
        RetrofitInstance.productService.getProducts().enqueue(object : Callback<ProductResponse> {

            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                swipeRefreshLayout.isRefreshing = false  // Stop the refresh spinner

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE // Disable ProgressBar

                    val products = response.body()?.products
                    if (products != null) {
                        originalProductsList = products // Store the original list
                        productsList = products
                        val adapter = ProductAdapter(productsList, this@MainActivity, this@MainActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        recyclerView.adapter = adapter
                        for(i in products){
                            brandList.add(i.brand)
                            categoryList.add(i.category)
                            Log.d(TAG, "onResponse: 1288==> ${i.brand} ${i.category}")
                        }
                    } else {
                        // Handle the case where the products list is null
                    }
                } else {
                    // Handle error responses
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("API_ERROR", "Error fetching products: $errorMessage")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false  // Stop the refresh spinner

                progressBar.visibility = View.GONE // Disable ProgressBar
                Toast.makeText(this@MainActivity, "Products not fetched!", Toast.LENGTH_SHORT).show()
                // Handle other types of errors, like network failures
                Log.e("NETWORK_ERROR", "Failed to fetch products", t)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    performSearch(newText) // Call search function
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun performSearch(query: String) {
        val filteredList = originalProductsList.filter {
            it.title.contains(query, ignoreCase = true)
        }

        val adapter = ProductAdapter(filteredList, this@MainActivity, this@MainActivity)
        recyclerView.adapter = adapter
    }


    override fun onProductClick(product: Products) {
//        val bundle = Bundle()
//        bundle.putSerializable("product", product)

        Log.d(TAG, "onProductClick: $product")


        val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)

        startActivity(intent)


        Log.d(TAG, "onProductClick: $product")
        // Implement your logic here when a product is clicked
    }

    override fun onFilterApplied(
        brand: String,
        category: String,
        priceSort: FilterDialogFragment.PriceSort,
        discountSort: FilterDialogFragment.DiscountSort
    ) {
        filterProducts(brand, category, priceSort, discountSort)
    }

    private fun filterProducts(
        brand: String,
        category: String,
        priceSort: FilterDialogFragment.PriceSort,
        discountSort: FilterDialogFragment.DiscountSort
    ) {
        Log.d(TAG, "filterProducts: Brand: $brand, Category: $category, PriceSort: $priceSort, DiscountSort: $discountSort")

        var filteredProducts = originalProductsList.filter {
            (brand == "Select Brand" || it.brand == brand) && (category == "Select Category" || it.category == category)
        }

        when (priceSort) {
            FilterDialogFragment.PriceSort.INCREASING -> filteredProducts = filteredProducts.sortedBy { it.price }
            FilterDialogFragment.PriceSort.DECREASING -> filteredProducts = filteredProducts.sortedByDescending { it.price }
            else -> {} // Do nothing for NONE
        }

        when (discountSort) {
            FilterDialogFragment.DiscountSort.LOW_TO_HIGH -> filteredProducts = filteredProducts.sortedBy { it.discountPercentage }
            FilterDialogFragment.DiscountSort.HIGH_TO_LOW -> filteredProducts = filteredProducts.sortedByDescending { it.discountPercentage }
            else -> {} // Do nothing for NONE
        }

        // Now, update your RecyclerView adapter with the filtered and sorted products
        val adapter = ProductAdapter(filteredProducts, this@MainActivity, this@MainActivity)
        recyclerView.adapter = adapter
    }




//    private fun showFilterDialog() {
//        // Create an instance of your dialog fragment
//        val dialogFragment = FilterDialogFragment()
//        dialogFragment.show(supportFragmentManager, "filter_dialog")
//    }

    private fun showFilterDialog(brandList: HashSet<String>, categoryList: HashSet<String>) {
        val dialogFragment = FilterDialogFragment()

        // Create a bundle to pass data
        val bundle = Bundle()
        bundle.putStringArrayList("brandList", ArrayList(brandList))
        bundle.putStringArrayList("categoryList", ArrayList(categoryList))

        // Set arguments to the dialog fragment
        dialogFragment.arguments = bundle
        dialogFragment.filterListener = this


        dialogFragment.show(supportFragmentManager, "filter_dialog")
    }


    companion object {
        private val TAG = "MainActivity"
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000) // 2 seconds delay for the double back press
    }
}
