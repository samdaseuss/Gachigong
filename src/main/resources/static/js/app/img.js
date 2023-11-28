document.addEventListener('DOMContentLoaded', function() {
    const imageUrls = [
      '/img/img_1.png',
      '/img/img_6.png',
      '/img/img_5.png',
      '/img/img_7.png',
      '/img/img_8.png',
      '/img/img_9.png',
      '/img/img_10.png',
      // 추가 이미지 URL...
    ];

    // 각 .imgList.col-3 요소에 대해 실행
    document.querySelectorAll('.imgList.col-3').forEach(function(imageContainer) {
      // 무작위 이미지 URL 선택
      const randomImageUrl = imageUrls[Math.floor(Math.random() * imageUrls.length)];

      // 이미지 요소 생성
      const imgElement = document.createElement('img');
      imgElement.classList.add('group'); // 클래스 추가
      imgElement.src = randomImageUrl;
      imgElement.alt = '이미지';

      // 이미지를 감싸는 span 요소 생성
      const spanElement = document.createElement('span');
      spanElement.appendChild(imgElement);

      // 이미지를 추가할 부모 요소에 추가
      if (imageContainer) {
        imageContainer.appendChild(spanElement);
      } else {
        console.error('요소를 찾을 수 없습니다.');
      }
    });
  });