package com.sdd.sindikasi.utils;

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

	public static final String[] COLORS = { "danger-color-dark", "warning-color-dark", "success-color-dark",
			"info-color-dark" };
}
