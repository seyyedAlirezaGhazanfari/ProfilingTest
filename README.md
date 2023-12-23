# آشنایی با نحوه پروفایل برنامه (Profiling)
## بخش اول
نکته اول اینکه اجرای این برنامه تمام نمیشود و در میانه ان با مشکل heap size روبرو میشود و به همین منظور فکر میکنم که اطلاعات مربوط به تابع نهایی برنامه را نمیتوانیم داشته باشیم.
به غیر از مورد بالا طبق دستور کار گفته شده ابزار مورد نیاز را نصب کرده ایم و سپس به سراغ کلاس گفته شده رفته ایم و آن را با استفاده از گزینه profiling موجود پروفایل کردیم. در ابتدا باید سه عدد به عنوان ورودی به این برنامه داد تا شروع به اجرای برنامه کند. از انجایی که مهم نبود این سه عدد چه مقادیری داشته باشند پس ما سه ۱ به عنوان ورودی های برنامه وارد کردیم.

![Screenshot from 2023-12-22 22-03-20](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/51c72d17-1aca-4db5-8fd3-9470529cbabf)
تصویر بالا نمایش دهنده شرایطی است که در اجرای برنامه ایجاد میشود.
حال باید نمودار های موجود در ابزار را بررسی کنیم تا بتوانیم پیک این نمودار ها را بدست بیاوریم و ببینیم کدام یک از توابع سبب ایجاد این پیک ها شده اند.

![Screenshot from 2023-12-22 22-04-40](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/4ae93767-f738-4605-9c06-befd98dd2dbf)

![Screenshot from 2023-12-22 22-04-55](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/e042d9a9-2c41-43a1-9d55-9d05d1d2cc60)

![Screenshot from 2023-12-22 22-05-17](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/ee09bddb-18bf-42bb-8138-c8bd36cdbc31)

همانطور که در تصاویر بالا میبینید هم مصرف حافظه و هم cpu time بیشتر برای تابع temp خرج شده اند که یک تابع است که میتوانیم بدنه آن را در تصویر پایین مشاهده کنیم. دو لوپ تو در تو به طول اولی 10K و دومی 20K که در داخل این دو لوپ دو index را جمع میزند و در یک list میریزد.

![Screenshot from 2023-12-22 22-07-17](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/ebae48c5-e24a-4b3c-b51b-d8834ad3ddc6)

برای بهتر کردن این تابع از بدنه و لاجیک زیر استفاده میکنیم.

![Screenshot from 2023-12-22 22-12-49](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/a7e6439c-39e6-44d0-bdd9-0feae320c05d)

بدین ترتیب با دوری از استفاده از دیتاتایپ arraylist برای ساختن یک لیست داینامیک از یک آرایه چندبعدی استاتیک استفاده میکنیم و این تغییر رویه تا حد بسیار خوبی مشکلات استفاده از منابع را حل میکند بطوری که میتوانید پایان برنامه خود را ببینیم و البته نمودار های ابزار هم گواه این ماجرا هستند.

![Screenshot from 2023-12-22 22-15-48](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/d76fcb4a-264b-4912-9d12-12a9be8e9137)


![Screenshot from 2023-12-22 22-16-03](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/59168138/2dbc6223-6605-405b-980b-2aebb4b779ce)

دقت کنید که در تصاویر حالت قبل ما نزدیک 3G رم را کامل پر کرده بودیم در صورتی که در این پیاده سازی صرفا 200M نهایت استفاده تابعمان بوده است. مورد دیگری که میتوان از این تصاویر برداشت کرد این است که نزدیک ۸۰ درصد مصرف cpu time را به نزدیک ۳۰ درصد کاهش داده ایم.

## بخش دوم
در اینجا یک کلاس برای پیاده سازی LinkedList دو طرفه و با دو تابع sortedInsert و hasDuplicates در کلاس Node پیاده سازی شده و سپس این توابع در کلاس NodeRunner استفاده شده اند.
در اجرای اولیه منابع مصرفی به صورت زیر است:
![image](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/56260232/5b414488-4ea3-4adf-9c7b-f16bc63d4b11)
![image](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/56260232/24bfa8c9-2c59-46da-9410-d54cc003d101)
![image](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/56260232/1bc981fe-fb0f-42f9-b39e-b277c3d3a399)

از مجموع تصاویر نتیجه گرفتیم که توابع sortedInsert و hasDuplicates و printLinkedList بیشترین مصرف منابع را دارند که با ایجاد تغییرات در [این کامیت](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/commit/013db2c4c14c409ba0f41f64a8ef18acde584530) مصرف منابع کاهش می‌یابد (کل زمان اجرا نیز چند ثانیه کاهش میابد). برای این کاهش در تابع hasDuplicates از الگوریتم با اوردر N به جای N^2 استفاده شده و در توابع دیگر نیز محاسبات هر چقدر ممکن بود از حلقه خارج شده.


![image](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/56260232/14a4732f-207f-4670-8113-f2760436169b)
![image](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/56260232/6a60c4c5-075a-41a3-a0a2-47b8636a254e)
![image](https://github.com/seyyedAlirezaGhazanfari/ProfilingTest/assets/56260232/5bfa8b9e-742e-49fe-836c-ef9f31700589)
زمان اجرا: از ۱۰.۳ ثانیه به ۶ ثانیه کاهش یافته.<br/>
مصرف CUP: در شکل مشخص است.<br/>
مصرف حافظه ماکسیمم heap: از ۱۶.۵ مگابایت به ۱۶ مگابایت کاهش یافته.<br/>
مصرف حافظه ماکسیمم non-heap: از ۲۱.۸۱ مگابایت به ۲۱ مگابایت کاهش یافته.





