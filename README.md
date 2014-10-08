ValFace
=======

ValFace is a validation interface that checks input formatting and acceptable values
based on a text specification.

### Dates

Date specifications allow the use of common phrases to produce a particular date.
Although in English, phrases such as "next week" or "last April" are imprecise, this
implementation imposes a strict adherence to their meaning. These are relative terms
associated with the current date and as such, their definition must be clear.

The basic date specification uses the ISO-8601 standard format.
<dl>
  <dt><b>&lt;year&gt;-&lt;month&gt;-&lt;day&gt;</b></dt>
  <dd>The specified date. ex: 2014-09-18</dd><br/>
</dl>
<dl>
  <dt><b>today</b> or <b>now</b></dt>
  <dd>The current date. ex: 2014-09-18</dd><br/>
  <dt><b>tomorrow</b></dt>
  <dd>The date after today. Same as: `today.plus.1.day`. ex: 2014-09-19</dd><br/>
  <dt><b>yesterday</b></dt>
  <dd>The date before today. Same as: `today.minus.1.day`. ex: 2014-09-17</dd><br/>
</dl>

A week is defined as follows:

* There are 5 weekdays; Monday, Tuesday, Wednesday, Thursday and Friday.
* There are 2 weekend days; Saturday and Sunday.
* The beginning of the week is Monday.
* The beginning of the weekend is Saturday.
* Each week is bracketed from Monday through Sunday.
* The term "this.week" refers to the enclosing week.
* The term "last.week" refers to days enclosed in the previous week.
* The term "next.week" refers to days enclosed in the following week.

| September 2014 |
| Mon | Tue | Wed | Thu | Fri | Sat | Sun |
|-----|-----|-----|-----|-----|-----|-----|
|  1  |  2  |  3  |  4  |  5  |  6  |  7  |
|  8  |  9  | 10  | 11  | 12  | 13  | 14  |
| 15  | 16  | 17  | 18  | 19  | 20  | 21  |
| 22  | 23  | 24  | 25  | 26  | 27  | 28  |
| 29  | 30  |     |     |     |     |     |
|-----------------------------------------|


* today or now - Today's date.

    today => 2014-09-18 

* next.year - January 1st of the next year.
* this.year - January 1st of the current year.
* last.year - January 1st of the previous year.
    

* next.month - The 1st of the next month.

    next.month => 2014-10-01

* next.<month-name> - The 1st of the named month.

    next.September => 2015-09-01
    
* next.week - The Monday of the next week.
* next.<day-of-week>
* last.year
* last.month
* last.<month-name>
* last.week
* last.<day-of-week>
* this.year
* this.month
* this.<month-name>
* this.week
* this.<day-of-week>
* <date>.plus.<number>.year(s)
* <date>.plus.<number>.month(s)
* <date>.plus.<number>.week(s)
* <date>.plus.<number>.day(s)
* <date>.minus.<number>.year(s)
* <date>.minus.<number>.month(s)
* <date>.minus.<number>.week(s)
* <date>.minus.<number>.day(s)