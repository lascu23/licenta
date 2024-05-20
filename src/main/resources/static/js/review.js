
const ratingBoxes = document.querySelectorAll(".rating-box");

ratingBoxes.forEach(ratingBox => {
    const stars = ratingBox.querySelectorAll(".stars i");
    stars.forEach((star, index1) => {
        star.addEventListener("click", () => {
            stars.forEach((star, index2) => {
                index1 >= index2 ? star.classList.add("active") : star.classList.remove("active");
            });
        });
    });
});

document.addEventListener("DOMContentLoaded", function() {
    const submitButtons = document.querySelectorAll(".submit-btn");

    submitButtons.forEach(button => {
        button.addEventListener("click", function() {
            const appointmentId = button.getAttribute("data-appointment-id");
            const activeStars = button.parentElement.querySelector(".stars").querySelectorAll(".active").length;
            window.location.href = `/appointmentReview/${appointmentId}/${activeStars}`;
        });
    });
});

