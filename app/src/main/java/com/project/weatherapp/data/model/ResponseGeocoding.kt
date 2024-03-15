package com.project.weatherapp.data.model

import com.google.gson.annotations.SerializedName


data class ResponseGeocodingItem(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("lon")
	val lon: Any,

	@field:SerializedName("state")
	val state: String,

	@field:SerializedName("lat")
	val lat: Any
)
