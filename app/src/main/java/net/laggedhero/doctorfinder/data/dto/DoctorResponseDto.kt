package net.laggedhero.doctorfinder.data.dto

data class DoctorResponseDto(
    val doctors: List<DoctorDto>,
    val lastKey: String?
)