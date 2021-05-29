<html>
<head>
    <link rel="stylesheet" href="/src/main/resources/css/employee/popup.css">
</head>
<body>
<div class="popup" id="popup">
    <div class="popup_body">
        <div class="popup_content">
            <a href="" class="popup_close"><img src="https://img.icons8.com/material-sharp/24/fa314a/close-window.png"/></a>
            <div class="popup_text">
                <p>
                <p>1. Id</p>
                You can go to the entity editing page by clicking on its id, in the table such id's are highlighted in
                blue.</p>
                <p>
                <p>2. Sort / Filter</p>
                To get the values you need in the table, you can filter it. For use regular expressions in filter fields.
                You can also choose sorting by clicking one of the buttons next to the filtering field.
                Please note that to enter the price and balance, it is necessary to separate the whole part of the number
                and everything after the comma.
                </p>
                <p>
                <p>3. Regular expression</p>
                You can use the "." instead of any character.
                Example: if you want to filter by id and enter the expression ".1" you will be shown all two-digit id whose
                last character is 1.
                You can use the "*" character instead of any sequence of characters. Example: if you want to filter by id
                and enter the expression "* 1", you will be shown everything, any length of id whose last character is 1.
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
