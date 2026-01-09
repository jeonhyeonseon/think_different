document.addEventListener('DOMContentLoaded', function() {
    const moreBtn = document.querySelector('.more-btn');
    const dropdown = document.querySelector('.dropdown');

    if(!moreBtn || !dropdown) {
        return;
    }

    moreBtn.addEventListener('click', function(e) {
        e.stopPropagation();
        dropdown.classList.toggle('open');
    });

    document.addEventListener('click', function() {
        dropdown.classList.remove('open');
    });

    dropdown.addEventListener('click', function(e) {
        e.stopPropagation();
    });
});