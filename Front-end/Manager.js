function viewProjectDetails(projectId) {
    window.location.href = `project.html?id=${projectId}`;
}


async function loadData() {
    try {
        const response = await fetch('data2.json');
        const data = await response.json();
        loadProjects(data.projects);
        loadDevelopers(data.developers);
    } catch (error) {
        console.error('Error loading JSON data:', error);
    }
}

// Load projects into the table and the dropdown
function loadProjects(projects) {
    const projectList = document.getElementById('projects-list');
    const projectSelect = document.getElementById('project-select');

    projects.forEach(project => {
        // Add projects to the table
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${project.name}</td>
            <td>${project.startDate}</td>
            <td><button href="#" onclick="viewProjectDetails(${project.id})">View Details</button></td>
        `;
        projectList.appendChild(row);

        // Add projects to the dropdown
        let option = document.createElement('option');
        option.value = project.id;
        option.text = project.name;
        projectSelect.add(option);
    });
}

// Load developers into the dropdown
function loadDevelopers(developers) {
    const developerSelect = document.getElementById('developer-select');

    developers.forEach(dev => {
        let option = document.createElement('option');
        option.value = dev.id;
        option.text = dev.name;
        developerSelect.add(option);
    });
}

// Example function to view project details
function viewProjectDetails(projectId) {
    alert("Viewing details for project ID: " + projectId);
}

// Send message to the developer
document.getElementById('login-form').addEventListener('submit', function (e) {
    e.preventDefault();
    const projectId = document.getElementById('project-select').value;
    const developerId = document.getElementById('developer-select').value;
    const message = document.getElementById('message').value;

    // Placeholder function to send a message
    alert(`Message sent to Developer ${developerId} for Project ${projectId}: ${message}`);
});

// Initialize the page by loading JSON data
loadData();

