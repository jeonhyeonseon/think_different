function getBucketListRoulettePool() {
    return Array.from(document.querySelectorAll('#flipPoolData li')).map(function (li) {
        return {
            id: li.dataset.id,
            title: li.dataset.title,
            priority: li.dataset.priority,
            place: li.dataset.place,
            season: li.dataset.season
        };
    });
}

function handleFlipCardClick() {
    const cardInner = document.getElementById('flipCardInner');

    if (cardInner.classList.contains('flipped') || cardInner.classList.contains('shuffling')) {
        return;
    }

    const pool = getBucketListRoulettePool();

    if (pool.length === 0) {
        return;
    }

    cardInner.classList.add('shuffling');

    cardInner.addEventListener('animationend', function onShuffleEnd() {
        cardInner.removeEventListener('animationend', onShuffleEnd);
        cardInner.classList.remove('shuffling');

        const picked = pool[Math.floor(Math.random() * pool.length)];
        revealBucketListCard(picked);
    });
}

function revealBucketListCard(item) {
    const cardInner = document.getElementById('flipCardInner');

    document.getElementById('flipCardTagPriority').textContent = item.priority;
    document.getElementById('flipCardTagPlace').textContent = item.place;
    document.getElementById('flipCardTagSeason').textContent = item.season;
    document.getElementById('flipCardTitle').textContent = item.title;
    document.getElementById('flipCardLink').href = '/bucketlist?highlight=' + encodeURIComponent(item.id);

    cardInner.classList.add('flipped');

    document.getElementById('flipResetBtn').disabled = false;
}

function resetFlipCard() {
    const cardInner = document.getElementById('flipCardInner');

    cardInner.classList.remove('flipped');

    document.getElementById('flipResetBtn').disabled = true;
}
