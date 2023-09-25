package ai.igenius.composestories.storiesui.models

import android.os.Parcel
import android.os.Parcelable

internal data class TreeNodeListValue(
    val selectedNodeId: Int?,
    val openedFolders: List<String>,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createStringArrayList()?.toList() ?: emptyList()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(selectedNodeId)
        parcel.writeStringList(openedFolders)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TreeNodeListValue> {
        override fun createFromParcel(parcel: Parcel): TreeNodeListValue {
            return TreeNodeListValue(parcel)
        }

        override fun newArray(size: Int): Array<TreeNodeListValue?> {
            return arrayOfNulls(size)
        }
    }
}