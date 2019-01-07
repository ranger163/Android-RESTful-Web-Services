package me.inassar.restful.model

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import me.inassar.restful.database.ItemsTable

data class DataItem(
    var itemId: String? = "",
    var itemName: String? = "",
    var category: String? = "",
    var description: String? = "",
    var sortPosition: Int? = -1,
    var price: Double? = -1.0,
    var image: String? = ""
) : Parcelable {

    constructor() : this(
        "", "",
        "", "",
        -1, -1.0,
        ""
    )

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    )

    fun toValues(): ContentValues {
        val values = ContentValues(7)
        return values.apply {
            put(ItemsTable.COLUMN_ID, itemId)
            put(ItemsTable.COLUMN_NAME, itemName)
            put(ItemsTable.COLUMN_DESCRIPTION, description)
            put(ItemsTable.COLUMN_CATEGORY, category)
            put(ItemsTable.COLUMN_POSITION, sortPosition)
            put(ItemsTable.COLUMN_PRICE, price)
            put(ItemsTable.COLUMN_IMAGE, image)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemId)
        parcel.writeString(itemName)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeValue(sortPosition)
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