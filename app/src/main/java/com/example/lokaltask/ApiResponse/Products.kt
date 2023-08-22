package com.example.lokaltask.ApiResponse

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Products(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("discountPercentage")
    val discountPercentage: Double,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("brand")
    val brand: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("images")
    val images: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeInt(price)
        dest.writeDouble(discountPercentage)
        dest.writeDouble(rating)
        dest.writeInt(stock)
        dest.writeString(brand)
        dest.writeString(category)
        dest.writeString(thumbnail)
        dest.writeStringList(images)
    }

    companion object CREATOR : Parcelable.Creator<Products> {
        override fun createFromParcel(parcel: Parcel): Products {
            return Products(parcel)
        }

        override fun newArray(size: Int): Array<Products?> {
            return arrayOfNulls(size)
        }
    }
}

