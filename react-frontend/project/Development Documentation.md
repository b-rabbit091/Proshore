### Development Documentation

This Documentation includes description of every files and folders mainly inside /src folder.
As most of the workings are carried out from /src folder , below are the important files and folders

 * __App.css__ : This file contains CSS styles specific to the App component.
 * __App.js__ : This is the main component of the application. It renders other components and manages the state of the application.
 * __App.test.js__ : This file contains test cases for the App component.
 * __ component__ : This folder contains two components, BatteryForm.js and BatterySearch.js. These components are used in the App.js file.
 * __css__ : This folder contains CSS modules used in the BatteryForm.js and BatterySearch.js components.
 * __index.css__ : This file contains global CSS styles for the application.
 * __index.js: This is the main entry point of the application. It renders the App component in the DOM.
 * __logo.svg__ : This is the logo image of the application.
 * __reportWebVitals.js__ : This file contains code to report web vitals (performance metrics) to the server.
 * __setupTests.js__ : This file contains configuration for running test cases.
 * __testComponent__ : This folder contains test cases for the BatteryForm.js and BatterySearch.js components.


Following are the components used for project.

I. BatteryForm
II. BatterySearch

---
### 1. BatteryFrom

## Overview

* This is a functional component named BatteryForm.
* It imports React, useState and useNavigate from react and react-router-dom respectively.
* It also imports some icons from @fortawesome/free-solid-svg-icons and the FontAwesomeIcon component from @fortawesome/react-fontawesome.
* It imports some styles from ../css/BatteryForm.module.css.
* It defines a state variable batteries and three more state variables named name, postcode, and capacity using the useState hook.
* It defines two functions: handleBatterySearch() and handleAddBattery() which change state variables.
* handleSubmit(event) is a function which is triggered when the submit button is clicked. It prevents the default behavior of the form and sends a POST request to the server to add the batteries to the database.
* It returns a JSX code block containing a form, inputs for the name, postcode, and capacity, an "Add Battery" button, and a "Submit" button.
* It also displays a list of added batteries along with their details, which can be removed using a trash icon button.
* It displays the count of added batteries in the "Submit" button along with a check-circle icon.
* It also displays a "Battery Search" button at the top-right corner of the screen, which redirects the user to the battery search page when clicked.


---
### 2. Battery Search

## Overview

* The code imports the React and useState components from the react library and the useNavigate function from the react-router-dom library.
* It also imports the styles object from a CSS module located at ../css/BatterySearch.module.css.
* A functional component BatterySearch is defined that makes use of the imported useState and useNavigate hooks.
* Within the BatterySearch component, there are three states that are defined using useState: startPostcode, endPostcode, and batteryDetails.
* The handleBatteryForm function is defined to navigate the user back to the Battery Form page.
* The handleSearch function sends a GET request to the server to retrieve battery information based on a postcode range entered by the user.
* If the request is successful, the server sends a JSON response which is parsed and set as the batteryDetails state.
* If there is an error with the GET request, the error is logged to the console.
* The JSX returned by the BatterySearch component includes an HTML form with two input fields for startPostcode and endPostcode values, a Search button, and a Results section to display the returned battery details.
* The Search button is set to trigger the handleSearch function when clicked.
* If the batteryDetails state is not null, the Results section is displayed, showing a table of the returned battery information.

