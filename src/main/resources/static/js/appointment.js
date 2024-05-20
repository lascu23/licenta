
    document.getElementById('dateInput').addEventListener('change', function() {
    var selectedDate = this.value;
    var currentDate = new Date().toISOString().split('T')[0];

    if (selectedDate < currentDate) {
    alert("You cannot select a date earlier than the current one!");
    this.value = "";
    return;
}

    var formattedDate = new Date(selectedDate).toISOString().split('T')[0];

    var doctorId = [[${appointment.doctorProfile.id}]];

    fetch('/getAvailableHours?doctorId=' + doctorId + '&date=' + formattedDate)
    .then(response => response.json())
    .then(data => {
    var select = document.getElementById('selectOptions');
    select.innerHTML = '';

    var option = document.createElement('option');
    option.text = 'Select an hour';
    select.add(option);

    data.forEach(function(hour) {
    var option = document.createElement('option');
    option.value = hour;
    option.text = hour;
    option.setAttribute('th:field', '*{appointmentHour}');
    select.add(option);
});
})
    .catch(error => console.error('Error:', error));
});
