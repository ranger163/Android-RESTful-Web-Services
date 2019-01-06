package me.inassar.restful.model

import android.os.Parcel
import android.os.Parcelable

data class DataItem(
    val itemName: String?,
    val category: String?,
    val description: String?,
    val sort: Int?,
    val price: Double?,
    val image: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemName)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeValue(sort)
        parcel.writeValue(price)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataItem> {
        override fun createFromParcel(parcel: Parcel): DataItem {
            return DataItem(parcel)
        }

        override fun newArray(size: Int): Array<DataItem?> {
            return arrayOfNulls(size)
        }
    }
}