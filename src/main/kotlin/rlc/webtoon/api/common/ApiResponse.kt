package rlc.webtoon.api.common

data class ApiResponse<D>(
        val data: D,
        val message: String? = null
) {
    companion object {
        fun <D> ofStatus(data: D) =
                runCatching {
                    data
                }.onSuccess {
                    ApiResponse(
                            DataResult.SUCCESS
                    )
                }.onFailure {
                    ApiResponse(
                            DataResult.FAILURE,
                            it.message
                    )
                }

    }
}

enum class DataResult {
    SUCCESS, FAILURE
}