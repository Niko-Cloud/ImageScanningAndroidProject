package com.ch2ps126.tutorin.data.remote.response

import com.google.gson.annotations.SerializedName

data class EquipmentResponse(

	@field:SerializedName("EquipmentResponse")
	val equipmentResponse: List<EquipmentResponseItem?>? = null
)

data class MusclesItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("targetMuscleName")
	val targetMuscleName: String? = null,

	@field:SerializedName("targetMuscleId")
	val targetMuscleId: Int? = null,

	@field:SerializedName("equipmentTargetMuscles")
	val equipmentTargetMuscles: EquipmentTargetMuscles? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class EquipmentResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("muscles")
	val muscles: List<MusclesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("equipmentImage")
	val equipmentImage: String? = null,

	@field:SerializedName("videoTutorialLink")
	val videoTutorialLink: String? = null,

	@field:SerializedName("tutorial")
	val tutorial: List<String?>? = null,

	@field:SerializedName("equipmentId")
	val equipmentId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class EquipmentTargetMuscles(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("targetMuscleTargetMuscleId")
	val targetMuscleTargetMuscleId: Int? = null,

	@field:SerializedName("equipmentEquipmentId")
	val equipmentEquipmentId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
