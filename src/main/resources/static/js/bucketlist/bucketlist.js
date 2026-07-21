let selectedBucketList = null;

const PLACE_TYPE_LABELS = { INDOOR: '실내', OUTDOOR: '야외' };
const SEASON_LABELS = { SPRING: '봄', SUMMER: '여름', FALL: '가을', WINTER: '겨울', ALL_SEASON: '사계절' };
const PRIORITY_LABELS = { MUST_DO: '꼭 하고 싶어요', SOMEDAY: '언젠가', HOLD: '보류' };

function openCreateModal() {
    const form = document.getElementById('bucketListForm');

    document.getElementById('modalTitle').textContent = '버킷리스트 등록';
    form.reset();

    document.getElementById('bucketListModal').classList.add('show');
}

function closeCreateModal() {
    document.getElementById('bucketListModal').classList.remove('show');
}

function openDetailModal(card) {
    selectedBucketList = {
        id: card.dataset.id,
        title: card.dataset.title,
        memo: card.dataset.memo || '',
        placeType: card.dataset.placeType,
        season: card.dataset.season,
        priority: card.dataset.priority
    };

    document.getElementById('detailTitle').textContent = selectedBucketList.title;

    document.getElementById('detailMemo').textContent =
        selectedBucketList.memo.trim() !== '' ? selectedBucketList.memo : '등록된 메모가 없습니다.';

    document.getElementById('detailPlaceType').textContent = PLACE_TYPE_LABELS[selectedBucketList.placeType] || '';
    document.getElementById('detailSeason').textContent = SEASON_LABELS[selectedBucketList.season] || '';
    document.getElementById('detailPriority').textContent = PRIORITY_LABELS[selectedBucketList.priority] || '';

    document.getElementById('deleteForm').action = `/bucketlist/${selectedBucketList.id}/delete`;
    document.getElementById('detailEditForm').action = `/bucketlist/${selectedBucketList.id}/edit`;

    switchToViewMode();

    document.getElementById('bucketListDetailModal').classList.add('show');
}

function closeDetailModal() {
    document.getElementById('bucketListDetailModal').classList.remove('show');
    switchToViewMode();
}

function switchToEditMode() {
    if (!selectedBucketList) {
        return;
    }

    document.getElementById('detailViewArea').classList.add('hidden');
    document.getElementById('detailEditForm').classList.remove('hidden');

    document.getElementById('detailTitle').textContent = '버킷리스트 수정';

    document.getElementById('editTitle').value = selectedBucketList.title;
    document.getElementById('editMemo').value = selectedBucketList.memo;
    document.getElementById('editPlaceType').value = selectedBucketList.placeType;
    document.getElementById('editSeason').value = selectedBucketList.season;
    document.getElementById('editPriority').value = selectedBucketList.priority;
}

function switchToViewMode() {
    document.getElementById('detailViewArea').classList.remove('hidden');
    document.getElementById('detailEditForm').classList.add('hidden');

    if (selectedBucketList) {
        document.getElementById('detailTitle').textContent = selectedBucketList.title;
    }
}

window.addEventListener('click', function (event) {
    const createModal = document.getElementById('bucketListModal');
    const detailModal = document.getElementById('bucketListDetailModal');

    if (event.target === createModal) {
        closeCreateModal();
    }

    if (event.target === detailModal) {
        closeDetailModal();
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const today = new Date().toLocaleDateString('en-CA', { timeZone: 'Asia/Seoul' });

    document.querySelectorAll('.schedule-date-input').forEach(function (input) {
        input.min = today;
        input.value = today;
    });
});
