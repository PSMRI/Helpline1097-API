package com.iemr.helpline1097.service.co.beneficiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory;
import com.iemr.helpline1097.data.co.services.SubCategoryDetails;
import com.iemr.helpline1097.repository.co.beneficiary.BenCalServiceCatSubcatMappingRepo;
import com.iemr.helpline1097.repository.co.services.DirectoryMappingRepository;
import com.iemr.helpline1097.repository.co.services.SubCategoryRepository;
import com.iemr.helpline1097.utils.config.ConfigProperties;

@ContextConfiguration(classes = { BenInformationCounsellingFeedbackReferralImpl.class, ConfigProperties.class })
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BenInformationCounsellingFeedbackReferralImplDiffblueTest {
	@MockBean
	private BenCalServiceCatSubcatMappingRepo benCalServiceCatSubcatMappingRepo;

	@Autowired
	private BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl;

	@MockBean
	private DirectoryMappingRepository directoryMappingRepository;

	@MockBean
	private SubCategoryRepository subCategoryRepository;

	@Test
	void testSaveBenCallServiceCatSubCat() {
		BenCallServicesMappingHistory benCallServicesMappingHistory1 = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory1.setBenCall97ServiceMapID(12L);
		benCallServicesMappingHistory1.setBenCallID(23L);
		benCallServicesMappingHistory1.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory1.setCategoryID(23);
		benCallServicesMappingHistory1.setCoCategoryID(12);
		benCallServicesMappingHistory1.setCoSubCategoryID(1234);
		benCallServicesMappingHistory1.setCreatedBy("A K");
		benCallServicesMappingHistory1.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setDeleted(false);
		benCallServicesMappingHistory1.setFeedbackID(129L);
		benCallServicesMappingHistory1.setInstituteDirMapID(234L);
		benCallServicesMappingHistory1.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory1.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory1.setSubCategoryID(12);
		benCallServicesMappingHistory1.setSubServiceID(234);

		ArrayList<BenCallServicesMappingHistory> list = new ArrayList<>();
		list.add(benCallServicesMappingHistory1);

		Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = list;

		SubCategoryDetails subCategoryDetails = new SubCategoryDetails();

		subCategoryDetails.setCategoryID(1);
		subCategoryDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		subCategoryDetails.setCreatedDate(mock(Timestamp.class));
		subCategoryDetails.setDeleted(true);
		subCategoryDetails.setLastModDate(mock(Timestamp.class));
		subCategoryDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		subCategoryDetails.setSubCatFilePath("/directory/foo.txt");
		subCategoryDetails.setSubCategoryDesc("Sub Category Desc");
		subCategoryDetails.setSubCategoryID(1);
		subCategoryDetails.setSubCategoryName("Sub Category Name");

		subCategoryDetails.toString();

		List<SubCategoryDetails> resultSet = new ArrayList<SubCategoryDetails>();
		resultSet.add(subCategoryDetails);

		BenCallServicesMappingHistory obj = benCallServicesMappingHistory1;

		when(benCalServiceCatSubcatMappingRepo.save(obj)).thenReturn(obj);

		assertNotNull(subCategoryDetails.getSubCatFilePath());
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl
				.saveBenCallServiceCatSubCat(benCallServicesMappingHistory));
	}

	@Test
	void testSaveBenCallServiceCatSubCat_AsNull() {
		BenCallServicesMappingHistory benCallServicesMappingHistory1 = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory1.setBenCall97ServiceMapID(12L);
		benCallServicesMappingHistory1.setBenCallID(23L);
		benCallServicesMappingHistory1.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory1.setCategoryID(23);
		benCallServicesMappingHistory1.setCoCategoryID(12);
		benCallServicesMappingHistory1.setCoSubCategoryID(1234);
		benCallServicesMappingHistory1.setCreatedBy("A K");
		benCallServicesMappingHistory1.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setDeleted(false);
		benCallServicesMappingHistory1.setFeedbackID(129L);
		benCallServicesMappingHistory1.setInstituteDirMapID(234L);
		benCallServicesMappingHistory1.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory1.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory1.setSubCategoryID(12);
		benCallServicesMappingHistory1.setSubServiceID(234);

		ArrayList<BenCallServicesMappingHistory> list = new ArrayList<>();
		list.add(benCallServicesMappingHistory1);

		Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = list;

		SubCategoryDetails subCategoryDetails = new SubCategoryDetails();

		subCategoryDetails.setCategoryID(1);
		subCategoryDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		subCategoryDetails.setCreatedDate(mock(Timestamp.class));
		subCategoryDetails.setDeleted(true);
		subCategoryDetails.setLastModDate(mock(Timestamp.class));
		subCategoryDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		subCategoryDetails.setSubCatFilePath(null);
		subCategoryDetails.setSubCategoryDesc("Sub Category Desc");
		subCategoryDetails.setSubCategoryID(1);
		subCategoryDetails.setSubCategoryName("Sub Category Name");

		subCategoryDetails.toString();

		List<SubCategoryDetails> resultSet = new ArrayList<SubCategoryDetails>();
		resultSet.add(subCategoryDetails);

		BenCallServicesMappingHistory obj = benCallServicesMappingHistory1;

		when(benCalServiceCatSubcatMappingRepo.save(obj)).thenReturn(obj);

		assertNull(subCategoryDetails.getSubCatFilePath());
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl
				.saveBenCallServiceCatSubCat(benCallServicesMappingHistory));
	}

	@Test
	void testSaveBenCallServiceCOCatSubCat() {
		BenCallServicesMappingHistory benCallServicesMappingHistory1 = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory1.setBenCall97ServiceMapID(12L);
		benCallServicesMappingHistory1.setBenCallID(23L);
		benCallServicesMappingHistory1.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory1.setCategoryID(23);
		benCallServicesMappingHistory1.setCoCategoryID(12);
		benCallServicesMappingHistory1.setCoSubCategoryID(1234);
		benCallServicesMappingHistory1.setCreatedBy("A K");
		benCallServicesMappingHistory1.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setDeleted(false);
		benCallServicesMappingHistory1.setFeedbackID(129L);
		benCallServicesMappingHistory1.setInstituteDirMapID(234L);
		benCallServicesMappingHistory1.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory1.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory1.setSubCategoryID(12);
		benCallServicesMappingHistory1.setSubServiceID(234);

		ArrayList<BenCallServicesMappingHistory> list = new ArrayList<>();
		list.add(benCallServicesMappingHistory1);

		Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = list;

		SubCategoryDetails subCategoryDetails = new SubCategoryDetails();

		subCategoryDetails.setCategoryID(1);
		subCategoryDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		subCategoryDetails.setCreatedDate(mock(Timestamp.class));
		subCategoryDetails.setDeleted(true);
		subCategoryDetails.setLastModDate(mock(Timestamp.class));
		subCategoryDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		subCategoryDetails.setSubCatFilePath("/directory/foo.txt");
		subCategoryDetails.setSubCategoryDesc("Sub Category Desc");
		subCategoryDetails.setSubCategoryID(1);
		subCategoryDetails.setSubCategoryName("Sub Category Name");

		subCategoryDetails.toString();

		List<SubCategoryDetails> resultSet = new ArrayList<SubCategoryDetails>();
		resultSet.add(subCategoryDetails);

		BenCallServicesMappingHistory obj = benCallServicesMappingHistory1;

		when(benCalServiceCatSubcatMappingRepo.save(obj)).thenReturn(obj);

		assertNotNull(subCategoryDetails.getSubCatFilePath());
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl
				.saveBenCallServiceCOCatSubCat(benCallServicesMappingHistory));
	}

	@Test
	void testSaveBenCallServiceCOCatSubCat_AsNull() {
		BenCallServicesMappingHistory benCallServicesMappingHistory1 = new BenCallServicesMappingHistory();

		benCallServicesMappingHistory1.setBenCall97ServiceMapID(12L);
		benCallServicesMappingHistory1.setBenCallID(23L);
		benCallServicesMappingHistory1.setBeneficiaryRegID(123L);
		benCallServicesMappingHistory1.setCategoryID(23);
		benCallServicesMappingHistory1.setCoCategoryID(12);
		benCallServicesMappingHistory1.setCoSubCategoryID(1234);
		benCallServicesMappingHistory1.setCreatedBy("A K");
		benCallServicesMappingHistory1.setCreatedDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setDeleted(false);
		benCallServicesMappingHistory1.setFeedbackID(129L);
		benCallServicesMappingHistory1.setInstituteDirMapID(234L);
		benCallServicesMappingHistory1.setLastModDate(Timestamp.valueOf("2029-09-09 09:09:09"));
		benCallServicesMappingHistory1.setModifiedBy("beneficiaryRequest");
		benCallServicesMappingHistory1.setRequestID("beneficiaryRequest");
		benCallServicesMappingHistory1.setSubCategoryID(12);
		benCallServicesMappingHistory1.setSubServiceID(234);

		ArrayList<BenCallServicesMappingHistory> list = new ArrayList<>();
		list.add(benCallServicesMappingHistory1);

		Iterable<BenCallServicesMappingHistory> benCallServicesMappingHistory = list;

		SubCategoryDetails subCategoryDetails = new SubCategoryDetails();

		subCategoryDetails.setCategoryID(1);
		subCategoryDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
		subCategoryDetails.setCreatedDate(mock(Timestamp.class));
		subCategoryDetails.setDeleted(true);
		subCategoryDetails.setLastModDate(mock(Timestamp.class));
		subCategoryDetails.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
		subCategoryDetails.setSubCatFilePath(null);
		subCategoryDetails.setSubCategoryDesc("Sub Category Desc");
		subCategoryDetails.setSubCategoryID(1);
		subCategoryDetails.setSubCategoryName("Sub Category Name");

		subCategoryDetails.toString();

		List<SubCategoryDetails> resultSet = new ArrayList<SubCategoryDetails>();
		resultSet.add(subCategoryDetails);

		BenCallServicesMappingHistory obj = benCallServicesMappingHistory1;

		when(benCalServiceCatSubcatMappingRepo.save(obj)).thenReturn(obj);

		assertNull(subCategoryDetails.getSubCatFilePath());
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl
				.saveBenCallServiceCOCatSubCat(benCallServicesMappingHistory));
	}

	@Test
	void testGettersAndSetters() {
		BenInformationCounsellingFeedbackReferralImpl benInformationCounsellingFeedbackReferralImpl = new BenInformationCounsellingFeedbackReferralImpl();

		benInformationCounsellingFeedbackReferralImpl.setConfigProperties(new ConfigProperties());
		benInformationCounsellingFeedbackReferralImpl
				.getBenCalServiceCatSubcatMappingRepo(mock(BenCalServiceCatSubcatMappingRepo.class));
		benInformationCounsellingFeedbackReferralImpl
				.getDirectoryMappingRepository(mock(DirectoryMappingRepository.class));
		benInformationCounsellingFeedbackReferralImpl.getSubCategoryRepository(mock(SubCategoryRepository.class));
	}

	@Test
	void testSaveBenCalReferralMapping() {
		// Arrange, Act and Assert
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping("Referral Request"));
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(
				"com.iemr.helpline1097.data.co.beneficiarycall.BenCallServicesMappingHistory"));
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping("42"));
		assertEquals("[]", benInformationCounsellingFeedbackReferralImpl.saveBenCalReferralMapping(""));
	}
}
