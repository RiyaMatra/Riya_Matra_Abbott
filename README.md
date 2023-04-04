# Abbott-mobile-test-automation

### Project related relevant information
**Pre-Requisite** 
I have run this on a real android device.
Hence, to execute the code at your end -
1. Connect real device / simulator
2. Update android_config.properties with -
   i. Appropriate udid
   ii. localApkFilePath value needs to be updated with apk path downloaded at your system
3. Please feel free to change data of the form as per choice by entering json values in android_config.properties

### Base framework : UIHelper
1. I have created framework in such a way that same framework can be optimised
further with ios & web by adding more parameters in UIHelper class : setBy method
2. config.properties can have an updated value for os as ios/web
For that, ios_launchApp method can be created similarly in UIHelper class
3. Also, same TC can run on cloud device by creating execution=cloudPlatformName
& creating one more if-loop with change in host name
4. **The framework is designed taken into account Web element & not Mobile Element specifically
considering the future scope of same framework to work on web**
with web further. Hence, setBy method instead of using @AndroidFindBy
and @iOSXCUITFindby OR @FindBy annotations for inspection.

### Optimisation of code : /test/java folder
1. The code of test cases is written ins uch a way i.e. it is splitted
as per functionality & necessity
2. The priorities attribute ensures smooth functioning of app end-to-end
3. Mainly, I have used dependsOnMethods attribute here so that
For example : if HomeScreen is not displayed -> further TCs fails directly
4. The inter-dependency from one method to other is handled in such a way that -
When there is a site slowness/other issues - we ensure user is brought to
NEXT screen from PREVIOUS screen
5. The optimisation code results into -
i. High performance
ii. Less Time : As we are not logging out after every TC
iii. Point ii. is possible ONLY when dependency of TC1 & TC2 is handles correctly
by ensuring their correct order, writing code to handle navigation of NEXT TC if is not
depednent on PREVIOUS TCs
6. This results into huge time-saving when number of TCs are large in number.
For example : Cart icon is verified when testing Products screen.
So, if the icon is not displayed -> existing TC fails.
Also, next TC of Cart screen is NOT executed & FAILS directly
8. The TCs are well segregated depending upon functionality of app to be tested
9. Main screen classes are also structured nicely
10. **Advance** : Toast messages are also inspected but due to less time span, I haven't included that in the scope.
    (Toast messages appear when you input is invalid. For example : Don't enter name & click on CTA)

### How to run test ?
You can run it in three different ways :
1. I have created a new configuration RunWithReports where I have added EmailableReports as a listener.
   Reports for the same are generated as **emailable-report.html under target folder**
2. I have also ran the same by running via testng.xml file. Rt. click testng.xml -> Run.
Exported results of same with file name : **Test Results.html**
3. Lastly, I have also ran the TC as an entire suite by :
Right click on EndtoEndTC.java file -> Run

  
  
 
