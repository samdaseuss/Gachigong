function sendUpdateRequest(id) {
    const url = `/dday`;
    fetch(url, {
        method: 'Post',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        console.log('DELETE 요청이 성공적으로 전송되었습니다.');
    })
    .catch(error => {
        console.error('Error during DELETE request:', error);
    });
}

function sendDeleteRequest(id) {
    const url = `/dday/${id}`;
    fetch(url, {
        method: 'Post',
        headers: {'Content-Type': 'application/json'},
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        console.log('성공');
        location.reload(true);
    })
    .catch(error => {
        console.error('실패', error);
    });
}