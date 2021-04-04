package com.sdd.sindikasi.utils;

import java.math.BigDecimal;

public class AppUtils {

	public static final int JOBID_DIGIT_LENGTH = 4;
	public static final int AUTOCOMPLETE_MAXROWS = 13;
	public static final int AUTOCOMPLETE_MINLENGTH = 2;

	public static final String FILES_ROOT_PATH = "/files";
	public static final String REPORT_PATH = "/report/";
	public static final String DOC_PATH = "/doc/";
	public static final String QR_PATH = "/qr/";
	public static final String IMAGE_PATH = "/img";

	public static final String SCHEDULER_ENABLE_LABEL = "ENABLE";
	public static final String SCHEDULER_ENABLE_VALUE = "1";
	public static final String SCHEDULER_DISABLE_LABEL = "DISABLE";
	public static final String SCHEDULER_DISABLE_VALUE = "0";
	public static final String SCHEDULER_REPEAT_PERMINUTE = "PER MINUTE";
	public static final String SCHEDULER_REPEAT_ATHOUR = "AT HOUR";

	public static final String STATUS_WAITING_PROSES = "WP";
	public static final String STATUS_WAITING_APPROVAL = "WA";
	public static final String STATUS_PROGRESS = "PG";
	public static final String STATUS_APPROVE = "AP";
	public static final String STATUS_DECLINE = "DECLINE";
	public static final String STATUS_DONE = "DN";

	public static final String[] STATUS_PIPELINE = { STATUS_APPROVE, STATUS_DECLINE };

	public static final Integer FILESTATUS_SENT = 1;
	public static final Integer FILESTATUS_FAIL = -1;
	
	public static final String PAYTYPE_POKOK = "1";
	public static final String PAYTYPE_BUNGA = "2";
	public static final String PAYTYPE_FEE = "3";
	
	public static final String PARAM_GROUP_COMPANYDATA = "COMPANYDATA";
	public static final String PARAM_COMPANYNAME = "COMPANYNAME";
	public static final String PARAM_DIVISINAME = "DIVISINAME";
	public static final String PARAM_GROUPNAME = "GROUPNAME";
	public static final String PARAM_ADDRESS1 = "ADDRESS1";
	public static final String PARAM_ADDRESS2 = "ADDRESS2";
	public static final String PARAM_ADDRESS3 = "ADDRESS3";
	public static final String PARAM_ADDRESS4 = "ADDRESS4";
	public static final String PARAM_CITY = "CITY";
	public static final String PARAM_PEMIMPIN = "PEMIMPIN";
	
	public static final String PATH_RESOURCES_IMAGE = "/resources/images";
	public static final String PATH_TMP = "/tmp";

	public static final String[] COLORS = { "danger-color-dark", "warning-color-dark", "success-color-dark",
			"info-color-dark" };
	
	public static final BigDecimal AMOUNT_DIVIDER = new BigDecimal(1000000); 
}
