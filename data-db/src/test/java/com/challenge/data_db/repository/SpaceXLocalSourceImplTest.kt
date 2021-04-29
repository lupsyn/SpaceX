package com.challenge.data_db.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.MainCoroutineRule
import com.challenge.data.SpaceXLocalSource
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data_db.SpaceXDb
import com.challenge.data_db.dao.CompanyInfoDao
import com.challenge.data_db.dao.LaunchModelEntitiesDao
import com.challenge.data_db.entities.CompanyInfoEntity
import com.challenge.data_db.entities.LaunchModelEntities
import com.challenge.data_db.mappers.CompanyInfoEntityToRepositoryModelMapper
import com.challenge.data_db.mappers.DateUtils
import com.challenge.data_db.mappers.LaunchModelEntitiesToRepositoryModelMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXLocalSourceImplTest {
    private lateinit var underTest: SpaceXLocalSource

    @Mock
    lateinit var db: SpaceXDb

    @Mock
    lateinit var dateUtilsImpl: DateUtils

    @Mock
    lateinit var companyInfoRepositoryMapper: CompanyInfoEntityToRepositoryModelMapper

    @Mock
    lateinit var launchesRepositoryMapper: LaunchModelEntitiesToRepositoryModelMapper

    @Mock
    lateinit var companyInfoDao: CompanyInfoDao

    @Mock
    lateinit var launchModelEntitiesDao: LaunchModelEntitiesDao

    @Mock
    lateinit var companyInfoEntity: CompanyInfoEntity

    @Mock
    lateinit var companyInfoRepositoryModel: CompanyInfoRepositoryModel

    @Mock
    lateinit var launchesRepositoryModels: List<LaunchRepositoryModel>

    @Mock
    lateinit var launchModelEntities: LaunchModelEntities

    @Mock
    lateinit var listRepositoryModel: List<LaunchRepositoryModel>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        underTest = SpaceXLocalSourceImpl(
            db,
            dateUtilsImpl,
            companyInfoRepositoryMapper,
            launchesRepositoryMapper
        )
        given(db.companyInfoDao()).willReturn(companyInfoDao)
        given(db.launchModelEntitiesDao()).willReturn(launchModelEntitiesDao)
    }

    @Test
    fun `When there is no cache company info response will return null`() {
        runBlockingTest {
            val companyInfoEntityFlow = flow { emit(null) }

            given(companyInfoDao.getCachedResponse()).willReturn(companyInfoEntityFlow)

            underTest.getCompanyInfo()
                .collect {
                    assertEquals(it, null)
                }
        }
    }

    @Test
    fun `Emit cache company info response if it's present and not expired`() {
        runBlockingTest {
            val companyInfoEntityFlow = flow { emit(companyInfoEntity) }

            given(companyInfoDao.getCachedResponse()).willReturn(companyInfoEntityFlow)
            given(companyInfoEntity.timeStamp).willReturn(System.currentTimeMillis())
            given(dateUtilsImpl.getCurrentTimestamp()).willReturn(System.currentTimeMillis())
            given(companyInfoRepositoryMapper.toRepositoryModel(companyInfoEntity)).willReturn(
                companyInfoRepositoryModel
            )

            underTest.getCompanyInfo()
                .collect {
                    assertEquals(it, companyInfoRepositoryModel)
                }
        }
    }

    @Test
    fun `Emit null if  company info response is expired and clear cache`() {
        runBlockingTest {
            val companyInfoEntityFlow = flow { emit(companyInfoEntity) }
            given(companyInfoDao.getCachedResponse()).willReturn(companyInfoEntityFlow)
            given(companyInfoEntity.timeStamp).willReturn(1)
            given(dateUtilsImpl.getCurrentTimestamp()).willReturn(System.currentTimeMillis())

            underTest.getCompanyInfo()
                .collect {
                    assertEquals(null, null)
                }

            verify(companyInfoDao).clearTable()
        }
    }

    @Test
    fun `will clear company info cached response`() {
        runBlockingTest {
            underTest.clearCompanyInfos()

            verify(companyInfoDao).clearTable()
        }
    }

    @Test
    fun `will insert company info response`() {
        runBlockingTest {

            given(companyInfoRepositoryMapper.modelToRepositoryEntity(companyInfoRepositoryModel)).willReturn(
                companyInfoEntity
            )
            underTest.insertCompanyInfo(companyInfoRepositoryModel)

            verify(companyInfoDao).insertCachedResponse(companyInfoEntity)
        }
    }

    @Test
    fun `will insert launches response`() {
        runBlockingTest {
            given(launchesRepositoryMapper.launchRepositoryModelToEntity(launchesRepositoryModels))
                .willReturn(launchModelEntities)

            underTest.insertLaunches(launchesRepositoryModels)

            verify(launchModelEntitiesDao).insertResponse(launchModelEntities)
        }
    }

    @Test
    fun `will clear launches cached response`() {
        runBlockingTest {
            underTest.clearLaunches()

            verify(launchModelEntitiesDao).clearTable()
        }
    }

    @Test
    fun `When there is no cache launches response will return null`() {
        runBlockingTest {
            val launchesEntityFlow = flow { emit(null) }

            given(launchModelEntitiesDao.getCachedResponses()).willReturn(launchesEntityFlow)

            underTest.getAllLaunches()
                .collect {
                    assertEquals(it, null)
                }
        }
    }

    @Test
    fun `Emit launches response if it's present and not expired`() {
        runBlockingTest {
            val launchesEntityFlow = flow { emit(launchModelEntities) }

            given(launchModelEntitiesDao.getCachedResponses()).willReturn(launchesEntityFlow)
            given(launchModelEntities.timeStamp).willReturn(System.currentTimeMillis())
            given(dateUtilsImpl.getCurrentTimestamp()).willReturn(System.currentTimeMillis())

            given(launchesRepositoryMapper.toRepositoryModel(launchModelEntities)).willReturn(
                listRepositoryModel
            )

            underTest.getAllLaunches()
                .collect {
                    assertEquals(it, listRepositoryModel)
                }
        }
    }


    @Test
    fun `Emit null if launches response is expired and clear cache`() {
        runBlockingTest {
            val launchesEntityFlow = flow { emit(launchModelEntities) }

            given(launchModelEntitiesDao.getCachedResponses()).willReturn(launchesEntityFlow)
            given(launchModelEntities.timeStamp).willReturn(1)
            given(dateUtilsImpl.getCurrentTimestamp()).willReturn(System.currentTimeMillis())

            underTest.getAllLaunches()
                .collect {
                    assertEquals(null, null)
                }

            verify(launchModelEntitiesDao).clearTable()
        }
    }
}
