package com.example.yogikotlin

import java.util.*

class BaseCalendar {
    //변하지 않는 수 주의 길이, 달력의 줄 수
    companion object{
        const val weekLow = 7
        const val calendarLow = 6
    }
    //캘린더 불러오기
    val calendar  = Calendar.getInstance()
    var prevMonthTailOffset = 0
    var nextMonthHeadOffset = 0
    var currentMonthMaxDate = 0

    var data = arrayListOf<Int>()
    //현재시간 가져오기,init은 함수를 불러오지 않아도 자동적으로 실행하는 함수
    init {
        calendar.time = Date()
    }
    //Unit:return 값 없을 때 사용
    //달력 생성
    fun initBaseCalendar(refreshCallback: (Calendar)->Unit ){
        makeMonthDate(refreshCallback)
    }
    //이전달력이동
    fun changeToPrevMonth(refreshCallback: (Calendar) -> Unit){
        if (calendar.get(Calendar.MONTH) == 0){
            calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) - 1)
            calendar.set(Calendar.MONTH,Calendar.DECEMBER)
        } else{
            calendar.set((Calendar.MONTH),calendar.get(Calendar.MONTH) - 1)
        }
        //이동후 달력생성
        makeMonthDate(refreshCallback)
    }
    //다음달이동
    fun changeToNextMonth(refreshCallback: (Calendar) -> Unit){
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER){
            calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) +1)
            calendar.set(Calendar.MONTH,0)
        } else{
            calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH) +1)
        }
        //이동후 달력생성
        makeMonthDate(refreshCallback)
    }
    private fun makeMonthDate(refreshCallback: (Calendar) -> Unit){

        data.clear()
        calendar.set(Calendar.DATE,1)
        //그 달의 길이
        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        //저번달 끝 날
        prevMonthTailOffset =calendar.get(Calendar.DAY_OF_WEEK) -1

        makePrevMonthTail(calendar.clone() as Calendar)
        makeCurrentMonth(calendar)
        //다음달 첫 날 위치
        nextMonthHeadOffset = calendarLow*weekLow - (prevMonthTailOffset+currentMonthMaxDate)
        makeNextMonthHead()

        refreshCallback(calendar)

    }
    private fun makePrevMonthTail(calendar : Calendar){
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffSetDate = maxDate - prevMonthTailOffset
        //data는 숫자를 보여준다. 저번달의 날짜를 보여준다.
        for (i in 1..prevMonthTailOffset)data.add(++maxOffSetDate)
    }
    private fun makeCurrentMonth(calendar:Calendar){
        //이번달 날짜 숫자 표기
        for (i in 1..calendar.getActualMaximum(Calendar.DATE))data.add(i)
    }
    private fun makeNextMonthHead(){
        var date = 1
        //다음달 숫자 표시
        for (i in 1..nextMonthHeadOffset)data.add(date++)

    }



}