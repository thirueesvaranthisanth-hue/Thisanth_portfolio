
const counters = document.querySelectorAll(".counter");

counters.forEach(counter => {

    let count = 0;

    const updateCounter = () => {

        const target = +counter.getAttribute("data-target");

        if(count < target){

            count += Math.ceil(target / 100);

            counter.innerText = count;

            setTimeout(updateCounter, 20);

        }else{

            counter.innerText = target + "+";

        }
    };

    updateCounter();

});