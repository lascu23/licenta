    var medicineIndex = 0;

    function addMedicineField() {
    var medicineDiv = document.createElement("div");
    var medicineInput = document.createElement("input");
    var detailsInput = document.createElement("textarea");
    var br = document.createElement("br");

    medicineInput.setAttribute("type", "text");
    medicineInput.setAttribute("name", "medicineNames[" + medicineIndex + "]");
    medicineInput.setAttribute("class", "form-control");
    medicineInput.setAttribute("placeholder", "Medicine Name");

    detailsInput.setAttribute("type", "text");
    detailsInput.setAttribute("name", "details[" + medicineIndex + "]");
    detailsInput.setAttribute("class", "form-control");
    detailsInput.setAttribute("placeholder", "Details");

    medicineDiv.appendChild(medicineInput);
    medicineDiv.appendChild(detailsInput);
    medicineDiv.appendChild(br);

    document.getElementsByTagName("form")[0].insertBefore(medicineDiv, document.getElementsByTagName("button")[0]);

    medicineIndex++;
}
