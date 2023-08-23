<!DOCTYPE html>
<html>
<body>

  <h1>Lokal Products (Product Showcase Android App)</h1>

  <p>Welcome to the Lokal Products (Product Showcase Android App)! This app allows users to explore a curated list of products fetched
    from a JSON API. Users can view essential details of each product and access a comprehensive product details screen
    by clicking on a product item.</p>

  <h2>Features</h2>
  <ul>
    <li>Display a visually appealing list of products sourced from an API.</li>
    <li>Provide users with detailed information about each product through a product details screen.</li>
    <li>Deliver a seamless and intuitive user experience with a clean and engaging UI.</li>
  </ul>

  <h2>Getting Started</h2>
  <h3>Prerequisites</h3>
  <ul>
    <li>Android Studio (latest version recommended)</li>
  </ul>

  <h3>Installation</h3>
  <ol>
    <li><strong>Clone the Repository:</strong>
      <pre><code>git clone https://github.com/yourusername/product-showcase-app.git</code></pre>
    </li>
    <li><strong>Open in Android Studio:</strong>
      <ul>
        <li>Launch Android Studio.</li>
        <li>Select "Open an existing Android Studio project."</li>
        <li>Navigate to the <code>product-showcase-app</code> folder in the cloned repository and open it.</li>
      </ul>
    </li>
    <li><strong>Sync and Build:</strong>
      <ul>
        <li>Wait for the project to sync and build.</li>
      </ul>
    </li>
    <li><strong>Run the App:</strong>
      <ul>
        <li>Run the app using an emulator or a physical device.</li>
      </ul>
    </li>
  </ol>



  <h2>API Endpoint</h2>
  <p>The app fetches product data from the following API endpoint:</p>
  <ul>
    <li><strong>Endpoint:</strong> <code>https://dummyjson.com/products</code></li>
    <li><strong>Method:</strong> GET</li>
    <li><strong>Response Format:</strong> JSON</li>
  </ul>

  <h2>Libraries Used</h2>
  <ul>
    <li>Retrofit: Simplifying network requests.</li>
    <li>Glide: Efficiently loading and displaying images.</li>
    <li>RecyclerView: Structuring the product list view.</li>
    <li>Intent: Enabling seamless navigation to the product details screen.</li>
  </ul>


</body>

</html>
