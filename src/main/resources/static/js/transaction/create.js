document.addEventListener("DOMContentLoaded", function() {
    const typeSelect = document.getElementById("type");
    const categorySelect = document.getElementById("category");

    function filterCategory() {
        const selectType = typeSelect.value;

        Array.from(categorySelect.options).forEach(option => {
            if (option.dataset.type === selectType) {
                option.style.display = "block";
            } else {
                option.style.display = "none";
            }
        });

        const firstVisible = Array.from(categorySelect.options).find(opt => opt.style.display !== "none");

        if (firstVisible) {
            categorySelect.value = firstVisible.value;
        }
    }

    typeSelect.addEventListener("change", filterCategory);

    window.onload = filterCategory;
})