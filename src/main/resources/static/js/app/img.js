document.addEventListener('DOMContentLoaded', function() {
    const imageUrls = [
      '/img/img_1.png',
      '/img/img_6.png',
      '/img/img_5.png',
      '/img/img_7.png',
      '/img/img_8.png',
      '/img/img_9.png',
      '/img/img_10.png',
    ];
    document.querySelectorAll('.imgList.col-3').forEach(function(imageContainer) {
        const randomImageUrl = imageUrls[Math.floor(Math.random() * imageUrls.length)];
        const imgElement = document.createElement('img');
        imgElement.classList.add('group'); // 클래스 추가
        imgElement.src = randomImageUrl;
        imgElement.alt = '이미지';

        const spanElement = document.createElement('span');
        spanElement.appendChild(imgElement);

        if (imageContainer) {
            imageContainer.appendChild(spanElement);
        } else {
            console.error('요소를 찾을 수 없습니다.');
        }
    });
});