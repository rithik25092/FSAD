fetch('php/fetchEvents.php')
  .then(res => res.json())
  .then(data => {
    let output = '';
    data.forEach(event => {
      output += `
        <div class="event-card">
          <h3>${event.title}</h3>
          <p>${event.description}</p>
          <p>Date: ${event.event_date}</p>
          <button id="btn-${event.event_id}" onclick="register(${event.event_id})">
    Register
</button>
        </div>`;
    });
    document.getElementById('eventList').innerHTML = output;
  });

function register(eventId){

    fetch('php/registerEvent.php', {
        method: 'POST',
        headers: {
            'Content-Type':'application/x-www-form-urlencoded'
        },
        body: `event_id=${eventId}`
    })
    .then(res => res.text())
    .then(data => {

        // get message area
        const msg = document.getElementById("msg");

        if(data === "registered"){
            msg.innerText = "✅ Registered successfully!";

            // reload events list
            loadEvents();

            // reload user's registered events
            loadMyEvents();
        }
        else if(data === "already"){
            msg.innerText = "⚠ Already registered!";
        }
    });
}
function loadMyEvents(){

    fetch('php/getMyEvents.php')
    .then(res => res.json())
    .then(data => {

        console.log("My Events:", data);

        let output = '';

        if(data.length === 0){
            output = "<li>No events registered</li>";
        } else {
            data.forEach(e => {
                output += `<li>${e.title}</li>`;
            });
        }

        document.getElementById('myEvents').innerHTML = output;
    })
    .catch(err => console.log("Error:", err));
}
window.onload = function(){
    loadEvents();
    loadMyEvents();
}