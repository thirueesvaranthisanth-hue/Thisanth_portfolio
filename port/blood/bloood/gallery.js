const buttons=document.querySelectorAll(".filter-buttons button");
const images=document.querySelectorAll(".gallery .image");

buttons.forEach(button=>{

button.addEventListener("click",()=>{

document.querySelector(".active").classList.remove("active");

button.classList.add("active");

const filter=button.dataset.filter;

images.forEach(image=>{

if(filter==="all"){

image.style.display="block";

}

else{

if(image.classList.contains(filter))
image.style.display="block";

else
image.style.display="none";

}

});

});

});

// Lightbox

const galleryImages=document.querySelectorAll(".gallery img");

const lightbox=document.querySelector(".lightbox");

const lightboxImg=document.querySelector(".lightbox-img");

galleryImages.forEach(img=>{

img.addEventListener("click",()=>{

lightbox.style.display="flex";

lightboxImg.src=img.src;

});

});

document.querySelector(".close").onclick=()=>{

lightbox.style.display="none";

}

lightbox.onclick=(e)=>{

if(e.target===lightbox){

lightbox.style.display="none";

}

}