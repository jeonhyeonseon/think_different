const BUCKET_LIST_TAG_EMOJI = {
    '실내': '🏠',
    '야외': '🌿',
    '봄': '🌸',
    '여름': '🌞',
    '가을': '🍂',
    '겨울': '❄️',
    '사계절': '🌈',
    '꼭 하고 싶어요': '⭐',
    '언젠가': '🌙',
    '보류': '⏸️'
};

function formatBucketListTag(text) {
    const emoji = BUCKET_LIST_TAG_EMOJI[text];
    return emoji ? emoji + ' ' + text : text;
}

function getBucketListRoulettePool() {
    return Array.from(document.querySelectorAll('#flipPoolData li')).map(function (li) {
        return {
            id: li.dataset.id,
            title: li.dataset.title,
            priority: li.dataset.priority,
            place: li.dataset.place,
            season: li.dataset.season,
            memo: li.dataset.memo
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

    document.getElementById('flipCardTagPriority').textContent = formatBucketListTag(item.priority);
    document.getElementById('flipCardTagPlace').textContent = formatBucketListTag(item.place);
    document.getElementById('flipCardTagSeason').textContent = formatBucketListTag(item.season);
    document.getElementById('flipCardTitle').textContent = item.title;
    document.getElementById('flipCardLink').href = '/bucketlist?highlight=' + encodeURIComponent(item.id);

    const memoWrap = document.getElementById('flipCardMemoWrap');
    const hasMemo = !!(item.memo && item.memo.trim());

    memoWrap.hidden = !hasMemo;
    document.getElementById('flipCardMemo').textContent = hasMemo ? item.memo : '';

    cardInner.classList.add('flipped');

    document.getElementById('flipResetBtn').disabled = false;
}

function resetFlipCard() {
    const cardInner = document.getElementById('flipCardInner');

    cardInner.classList.remove('flipped');

    document.getElementById('flipResetBtn').disabled = true;
}
