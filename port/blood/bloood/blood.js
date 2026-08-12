// Blood group information database
const bloodData = {
    "O-": {
        donate: "All Blood Groups",
        receive: "O-"
    },
    "O+": {
        donate: "O+, A+, B+, AB+",
        receive: "O+, O-"
    },
    "A-": {
        donate: "A-, A+, AB-, AB+",
        receive: "A-, O-"
    },
    "A+": {
        donate: "A+, AB+",
        receive: "A+, A-, O+, O-"
    },
    "B-": {
        donate: "B-, B+, AB-, AB+",
        receive: "B-, O-"
    },
    "B+": {
        donate: "B+, AB+",
        receive: "B+, B-, O+, O-"
    },
    "AB-": {
        donate: "AB-, AB+",
        receive: "AB-, A-, B-, O-"
    },
    "AB+": {
        donate: "AB+",
        receive: "All Blood Groups"
    }
};

// Search function
function searchBlood() {

    // Get input value
    const input = document
        .getElementById("bloodGroup")
        .value
        .trim()
        .toUpperCase();

    // Get result div
    const result = document.getElementById("result");

    // Check empty input
    if (input === "") {
        result.innerHTML = "<p style='color:red;'>Please enter a blood group.</p>";
        return;
    }

    // Check blood group exists
    if (bloodData[input]) {

        const data = bloodData[input];

        result.innerHTML = `
            <div style="
                background: #ffffff;
                padding: 20px;
                margin-top: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                text-align: left;
            ">

                <h3 style="color:#d90429; margin-bottom:10px;">
                    Blood Group: ${input}
                </h3>

                <p><strong>Can Donate To:</strong> ${data.donate}</p>

                <p><strong>Can Receive From:</strong> ${data.receive}</p>

                <p style="color:green; margin-top:10px;">
                    ✔ Blood group found successfully.
                </p>

            </div>
        `;

    } else {

        result.innerHTML = `
            <p style="
                color: red;
                font-weight: bold;
                margin-top: 20px;
            ">
                ✖ Invalid blood group. Try O+, A-, AB+, etc.
            </p>
        `;
    }
}

// Optional: Press Enter key to search
document
    .getElementById("bloodGroup")
    .addEventListener("keypress", function(event) {

        if (event.key === "Enter") {
            searchBlood();
        }
    });
