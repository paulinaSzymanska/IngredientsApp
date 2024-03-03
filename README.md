## How to run app from terminal?

- Set adb environment variable
- Run emulator
- adb push recipes/breakfast.pdf /sdcard/Download
- adb push recipes/dinner.pdf /sdcard/Download
- adb push recipes/supper.pdf /sdcard/Download
- adb push recipes/dessert.pdf /sdcard/Download

and the run app 

## if you run app from phone there are few rules!

1. When you run app you have to apply recipe as a pdf file from phone
2. Recipe has to have "Składniki/SKŁADNIKI/składniki" and 
   "PRZYGOTOWANIE/przygotowanie/Przygotowanie" part without it will not work
3. There is a seperate site for every type of meal, so if u want to generate shop list for desserts
   you should type number sites in site called "Dessert"
4. list of ingredients has to start from number like "20g" or "kilka"
5. That's it enjoy!
