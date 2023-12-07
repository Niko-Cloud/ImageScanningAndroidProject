package com.ch2ps126.capstoneproject.data.remote.response

import com.google.gson.annotations.SerializedName

data class EquipmentResponse(

	@field:SerializedName("EquipmentResponse")
	val equipmentResponse: List<EquipmentResponseItem?>? = null
)

data class EquipmentResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("targetMuscles")
	val targetMuscles: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("equipmentImage")
	val equipmentImage: String? = null,

	@field:SerializedName("videoTutorialLink")
	val videoTutorialLink: String? = null,

	@field:SerializedName("tutorial")
	val tutorial: String? = null,

	@field:SerializedName("equipmentId")
	val equipmentId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
