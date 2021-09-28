package com.viatom.sevenweather.utils

import android.text.TextUtils

import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception
import java.util.*

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 11:39
 * 邮箱：sunhao@viatomtech.com
 * 类说明:
 */
object LoggerUtils {

    /**
     * @param tag  log tag
     * @param msg  log message
     * @param type log type
     */
    fun log(tag: String?, msg: String?, type: Int) {
        when (type) {
            VERBOSE -> v(tag, msg)
            DEBUG -> d(tag, msg)
            INFO -> i(tag, msg)
            WARN -> w(tag, msg)
            ERROR -> e(tag, msg)
            else -> {
            }
        }
    }

    /**
     * verbose log
     *
     * @param tag log tag
     * @param log log message
     */
    fun v(tag: String?, log: String?) {
        val msg = getStackTraceMessage(log)
        if (canPrint) {
            msg?.let { Log.v(tag, it) }
        }
        if (canWrite) {
            writeLog(msg)
        }
    }

    /**
     * debug log
     *
     * @param tag log tag
     * @param log log message
     */
    fun d(tag: String?, log: String?) {
        val msg = getStackTraceMessage(log)
        if (canPrint) {
            msg?.let { Log.d(tag, it) }
        }
        if (canWrite) {
            writeLog(msg)
        }
    }

    /**
     * info log
     *
     * @param tag log tag
     * @param log log message
     */
    fun i(tag: String?, log: String?) {
        val msg = getStackTraceMessage(log)
        if (canPrint) {
            msg?.let { Log.i(tag, it) }
        }
        if (canWrite) {
            writeLog(msg)
        }
    }

    /**
     * warn log
     *
     * @param tag log tag
     * @param log log message
     */
    fun w(tag: String?, log: String?) {
        val msg = getStackTraceMessage(log)
        if (canPrint) {
            msg?.let { Log.w(tag, it) }
        }
        if (canWrite) {
            writeLog(msg)
        }
    }

    /**
     * error log
     *
     * @param tag log tag
     * @param log log message
     */
    fun e(tag: String?, log: String?) {
        val msg = getStackTraceMessage(log)
        if (canPrint) {
            msg?.let { Log.e(tag, it) }
        }
        if (canWrite) {
            writeLog(msg)
        }
    }

    /**
     * write log to local file
     *
     * @param log log message
     */
    private fun writeLog(log: String?) {
        val logPath: String = if (useDefaultLogPath) {
            createMkdirsAndFiles(DEFAULT_LOG_DIR_PATH, logName)
        } else {
            createMkdirsAndFiles(null, null)
        }
        if (!TextUtils.isEmpty(logPath)) {
            val content: String =
                formatter.format(System.currentTimeMillis()).toString() + "\n" + log + "\n\n"
            write2File(logPath, content, true)
        }
    }

    /**
     * get jimi log output file name
     *
     * @return true means get crash file name,false means get log file name
     */
    private fun getFileName(): String {
        val date: String = format.format(Date())
        return "log_jimiTrafficBox_$date.txt"
    }

    /**
     * create directory and file
     *
     * @param path     log output directory name,
     * if it's null set JIMI_LOG_PATH as log output directory
     * @param filename log output file name,
     * if it's null set "log_jimiTrafficBox_yyyy-MM-dd.txt" as log output file name
     * @return log output file absolute path
     */
    private fun createMkdirsAndFiles(path: String?, filename: String?): String {
        var pathName = path
        var fileName = filename
        if (TextUtils.isEmpty(pathName)) {
            pathName = ""
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName()
        }
        val file = File(pathName)
        if (!file.exists()) {
            try {
                file.mkdirs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val f = File(file, fileName)
        if (!f.exists()) {
            try {
                f.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return f.absolutePath
    }

    /**
     * write log to local file
     *
     * @param path   log output file absolute path
     * @param text   output log content
     * @param append append flags,true means add log content to log output file end with
     */
    private fun write2File(path: String, text: String, append: Boolean) {
        var bw: BufferedWriter? = null
        try {
            bw = BufferedWriter(FileWriter(path, append))
            bw.write(text)
            // 换行刷新
            bw.newLine()
            bw.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (bw != null) {
                try {
                    bw.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * log output format，use thread stack trace
     */
    private const val stackTraceFormat = "at %s.%s(%s:%s)/%s"

    /**
     * format stack trace message
     *
     * @param element [StackTraceElement]
     * @param log     log content
     * @return
     */
    private fun formatStackTraceMessage(element: StackTraceElement, log: String?): String {
        return String.format(
            stackTraceFormat, element.className, element.methodName, element.fileName,
            element.lineNumber, log
        )
    }

    /**
     * printf stack trace message
     *
     * @param printf   print flags,true means print stack trace message
     * @param elements [StackTraceElement[]]
     */
    private fun printfStackTraceMessage(printf: Boolean, elements: Array<StackTraceElement>?) {
        if (printf && elements != null && elements.isNotEmpty()) {
            for (element in elements) {
                val msg = formatStackTraceMessage(element, "printfStackTraceMessage")
                writeLog(msg)
            }
            writeLog("########## printf end ##########")
        }
    }

    /**
     * get stack trace message
     *
     * @param log log content
     * @return
     */
    private fun getStackTraceMessage(log: String?): String? {
        val elements = Thread.currentThread().stackTrace
        if (elements.isNotEmpty()) {
            printfStackTraceMessage( /* true */false, elements)
            return formatStackTraceMessage(elements[4], log)
        }
        return null
    }
}