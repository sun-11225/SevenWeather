package com.viatom.sevenweather.utils

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 15:09
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 常量
 */

/**
 *  weather token
 */
const val TOKEN = "aiVSTQt7NtaBoWsv"

/**
 * weather base url
 */
const val BASE_URL = "https://api.caiyunapp.com/"

/**
 * exit app time
 */
const val TIME_EXIT = 1500


//************************************************ Log dependent constant *********************************
/**
 * VERBOSE
 */
const val VERBOSE = 5

/**
 * DEBUG
 */
const val DEBUG = 4

/**
 * INFO
 */
const val INFO = 3

/**
 * WARN
 */
const val WARN = 2

/**
 * ERROR
 */
const val ERROR = 1

/**
 * print log flags,true means open print log,default false
 */
const val canPrint = true

/**
 * write log flags,true means open write log,default false
 */
const val canWrite = false

/**
 * print log and write log default time format,yyyyMMddHHmmssSSS
 */
const val inFormat = "yyyy-MM-dd HH:mm:ss.SSS"

/**
 * log file name format
 */
val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")

/**
 * log content time format
 */
val formatter: SimpleDateFormat = SimpleDateFormat(inFormat)

/**
 * default log output directory name
 */
const val DEFAULT_LOG_DIR_PATH: String = ""

/**
 * default log output file name
 */
const val logName = "WeatherLog.txt"

/**
 * use default log output directory flags,default false
 */
const val useDefaultLogPath = false

//**************************************************************************************

/**
 * save place flag
 */
const val SAVE_PLACE = "place"

