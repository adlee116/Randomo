package com.example.loginandregistration.presentation.domain

import android.os.Parcelable
import com.example.loginandregistration.presentation.domain.errors.DomainError
import com.example.loginandregistration.presentation.domain.errors.ErrorWithMessage
import com.example.loginandregistration.presentation.network.PoliceRepo
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class GetPoliceListUseCase @Inject constructor(private val repo: PoliceRepo) :
    BaseUseCase<List<PoliceListItem>, Unit>() {
    override suspend fun run(params: Unit): Result<List<PoliceListItem>, DomainError> {
        return try {
            repo.getPoliceForcesList()?.let { Result.Success(it) } ?: run {
                Result.Failure(ErrorWithMessage(messageString = "Error"))
            }

        } catch (ex: Exception) {
            Result.Failure(
                ErrorWithMessage(
                    messageString = "Error"
                )
            )
        }
    }
}

@Parcelize
data class PoliceListItem(
    val id: String,
    val name: String
): Parcelable