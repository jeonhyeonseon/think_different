console.log('detail.js loaded');

document.addEventListener('DOMContentLoaded', function() {

    console.log('DOMContentLoaded in detail.js');

    const moreBtn = document.querySelector('.more-btn');
    const dropdown = document.querySelector('.dropdown');
    if(!moreBtn || !dropdown) return;

    moreBtn.addEventListener('click', function(e) {
        e.stopPropagation();
        console.log('more button clicked');
        dropdown.classList.toggle('open');
    });

    document.addEventListener('click', function() {
        dropdown.classList.remove('open');
    });

    dropdown.addEventListener('click', function(e) {
        e.stopPropagation();
    });
});