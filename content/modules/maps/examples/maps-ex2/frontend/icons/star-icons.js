import '@vaadin/icon/vaadin-iconset.js';

const template = document.createElement('template');

template.innerHTML = `<vaadin-iconset name="star-icons" size="24">
  <svg><defs>
    <g id="star"><path d="M12 .587l3.668 7.568 8.332 1.207-6 5.848 1.416 8.263L12 18.896l-7.416 3.895L6 14.162l-6-5.848 8.332-1.207z" fill="#FFD700"/></g>
    <g id="heart"><path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" fill="#FF69B4"/></g>
    <g id="circle"><circle cx="12" cy="12" r="10" fill="#1E90FF"/></g>
  </defs></svg>
</vaadin-iconset>`;

document.head.appendChild(template.content);