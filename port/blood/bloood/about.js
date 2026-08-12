
const counters = document.querySelectorAll(".counter");

const options = {
    threshold: 0.5
};

const observer = new IntersectionObserver(function(entries, observer){

    entries.forEach(entry => {

        if(entry.isIntersecting){

            const counter = entry.target;

            const target = +counter.dataset.target;

            let count = 0;

            const update = () => {

                const increment = Math.ceil(target / 100);

                if(count < target){

                    count += increment;

                    if(count > target){
                        count = target;
                    }

                    counter.innerText = count;

                    setTimeout(update,20);

                }

            };

            update();

            observer.unobserve(counter);

        }

    });

}, options);

counters.forEach(counter=>{
    observer.observe(counter);
});