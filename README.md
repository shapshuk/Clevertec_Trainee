# Clevertec Certification task

The task involved developing an application with the following functionalities:
- Retrieving meta-information from the server to construct a form
- Creating a dynamic form for data input based on the received information
- Allowing the user to enter data in the constructed form
- Sending the entered data to the server
- Displaying the response received from the server
- Showing a progress bar on the screen while sending and receiving data

The form was implemented as a table with two columns: the left column displayed the field name, and the right column contained the control for entering the field value. The fields supported three types of data: text input, numeric input, and dropdown. At the bottom of the form, an image from the "image" field was displayed, using Glide. The image was always visible on the screen, regardless of the table's height, which could be scrolled over it.

A button was provided for sending the entered values, and the form title was displayed in the standard panel at the top of the screen.

The result of the operation of sending data to the server was displayed in a dialog. The project was implemented using Dagger2, MVVM, and Clean Architecture principles.
