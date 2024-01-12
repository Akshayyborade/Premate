$(document).ready(function () {
    $(".slidebar").click(function () {
        $(".slidebar").removeClass("clicked"); // Remove the 'clicked' class from all divs
        $(this).addClass("clicked");   // Add it only to the currently clicked div
    });
});
// document.addEventListener("DOMContentLoaded", function () {
//     // Call the function with "dashboard-tab" option on page load
//     changeContent("dashboard-tab");
// });

function changeContent(option) {
    var dynamicContent = document.getElementById('content-dashboard');
    var timetable = document.getElementById('timetable');
    if (option != "dashboard-tab") {
        timetable.style.display = 'none';
    }
    switch (option) {
        case "dashboard-tab":
            dynamicContent.innerHTML = `
                <div class="dashboard" id="dashboard">
                    <div class="roster">
                        <div class="days-present">
                            <h5>Days Present:</h5>
                            <h4>20 days</h4>
                        </div>
                        <div class="days-absent">
                            <h5>Days Absent:</h5>
                            <h4>20 Days</h4>
                        </div>
                    </div>
                    <div class="marks-exam">
                        <div class="submit-Marks">
                            <h4>Submit Marks</h4>
                        </div>
                        <div class="sent-notice">
                            <h4>Notice</h4>
                        </div>
                    </div>
                </div>`;
            timetable.style.display = 'block';

            break;
        case "time-table-tab":
            dynamicContent.innerHTML = '<div id="about"><h2>About Us</h2>';
            break;
        case "student-info":
            dynamicContent.innerHTML = '<h2>Student</h2>';
            break;
        case "attendance":
            dynamicContent.innerHTML = '<h2>Attendance</h2>';
            break;
        case "exam":
            dynamicContent.innerHTML = '<h2>Exam</h2>';
            break;
        case "syllabus":
            dynamicContent.innerHTML = '<h2>Syllabus</h2>';
            break;
        case "salary":
            dynamicContent.innerHTML = '<h2>Salary</h2>';
            break;
        case "roster":
            dynamicContent.innerHTML = '<h2>Roster</h2>';
            break;
        case "notice":
            dynamicContent.innerHTML = '<h2>Notice</h2>';
            break;
      
    }
}
// timetable in dashboard
document.addEventListener("DOMContentLoaded", function () {
    // Set the initial date to the current day
    var startDate = new Date();
    var currentDayOfWeek = startDate.toLocaleDateString('en-US', { weekday: 'long' }).toLowerCase();

    // Generate dates dynamically for the current month
    startDate.setDate(1); // Set to the first day of the month

    while (startDate.getMonth() === new Date().getMonth()) {
        var dayOfWeek = startDate.toLocaleDateString('en-US', { weekday: 'long' });
        var dayOfMonth = startDate.getDate();

        var dateLink = document.createElement('a');
        dateLink.href = '#' + dayOfWeek.toLowerCase();
        dateLink.textContent = dayOfWeek.slice(0, 3) + ' ' + dayOfMonth; // Display shortened day names

        dateLink.addEventListener('click', function (event) {
            event.preventDefault();
            changeDay(event.target.textContent.trim().toLowerCase());
        });

        document.getElementById('dateScroll').appendChild(dateLink);

        startDate.setDate(startDate.getDate() + 1); // Move to the next day
    }

    // Add a class to the current date link
    var currentDateLink = document.querySelector('a[href="#' + currentDayOfWeek + '"]');
    if (currentDateLink) {
        currentDateLink.classList.add('active');
        // Scroll to center the active date link
        currentDateLink.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }

    // Set initial content based on the current day
    changeDay(currentDayOfWeek);
    changeContent("dashboard-tab"); // Uncomment this line to initially load the dashboard content
});

function changeDay(day) {

    // Sample content, replace with actual content retrieval logic
    var dayName = day.slice(0, 3).toLowerCase();
    console.log(dayName);
    var content = getSubjectTimetable(dayName);
    document.getElementById('content-div').innerHTML = content;
    //  // Remove the "active" class from all date links
    // var allDateLinks = document.querySelectorAll('#dateScroll a');
    // allDateLinks.forEach(function (link) {
    //     link.classList.remove('active');
    // });
    // Add the "active" class to the current date link
    var currentDateLink = document.querySelector('a[href="#' + dayName + '"]');
    if (currentDateLink) {
        currentDateLink.classList.add('active');
        // Scroll to center the active date link
        currentDateLink.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
}
// Function to retrieve subject timetable based on the day
function getSubjectTimetable(day) {
    // Replace with actual logic to fetch subject timetable for the specified day
    switch (day) {
        case 'mon':
            return '<h3>Monday</h3>';
        case 'tue':
            return 'Tuesday Timetable Content';
        case 'wed':
            return 'Wednesday Timetable Content';
        case 'thu':
            return 'Thursday Timetable Content';
        case 'fri':
            return 'Friday Timetable Content';
        case 'sat':
            return 'Saturday Timetable Content';
        default:
            return 'No timetable available for this day';
    }
}
