    function getAppointments(button) {
    const day = button.getAttribute('data-day');
    const month = button.getAttribute('data-month');
    const year = button.getAttribute('data-year');

    fetch(`/getAppointments?day=${day}&currentMonth=${month}&currentYear=${year}`)
    .then(response => response.json())
    .then(data => {

    const appointmentsDiv = document.getElementById('appointments');
    appointmentsDiv.innerHTML = '';
    data.forEach(appointment => {

    const appointmentDiv = document.createElement('div');
    appointmentDiv.classList.add('appointment');

    const nameDiv = document.createElement('div');
    nameDiv.innerHTML = `<strong>${appointment.patientLastName}, ${appointment.patientFirstName}</strong>`;
    appointmentDiv.appendChild(nameDiv);

    const dateTimeDiv = document.createElement('div');
    dateTimeDiv.textContent = `Date & Time: ${appointment.appointmentDate}, ${appointment.appointmentHour}`;
    appointmentDiv.appendChild(dateTimeDiv);

    const detailsDiv = document.createElement('div');
    detailsDiv.textContent = `Details: ${appointment.details}`;
    appointmentDiv.appendChild(detailsDiv);

    const fulfilledDiv = document.createElement('div');
    fulfilledDiv.classList.add('fulfilled');
    if (appointment.fulfilled) {
    fulfilledDiv.textContent = "Fulfilled";
} else {
    const button = document.createElement('button');
    button.textContent = "Fulfill";
    button.addEventListener('click', () => markAsFulfilled(appointment.id));
    fulfilledDiv.appendChild(button);

    const prescriptionButton = document.createElement('button');
    prescriptionButton.textContent = "Create Prescription";
    prescriptionButton.addEventListener('click', () => redirectToPrescriptionCreation(appointment.id));
    appointmentDiv.appendChild(prescriptionButton);
}
    appointmentDiv.appendChild(fulfilledDiv);

    appointmentsDiv.appendChild(appointmentDiv);
});
})
    .catch(error => console.log('Error fetching appointments:', error));
}

    function markAsFulfilled(appointmentId) {
    fetch(`/markAppointmentFulfilled/${appointmentId}`, {
        method: 'PUT'
    })
        .then(response => {
            if (response.ok) {
                console.log("Appointment marked as fulfilled successfully!");
            } else {
                console.error("Failed to mark appointment as fulfilled:", response.status);
            }
        })
        .catch(error => console.error('Error marking appointment as fulfilled:', error));
}

    function redirectToPrescriptionCreation(id) {
    window.location.href = `/createPrescription?appointmentId=${id}`;
}