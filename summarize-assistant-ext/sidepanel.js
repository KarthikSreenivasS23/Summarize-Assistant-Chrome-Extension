document.addEventListener('DOMContentLoaded', () => {
    chrome.storage.local.get(['researchNotes'], function(result) {
       if (result.researchNotes) {
        document.getElementById('notes').value = result.researchNotes;
       } 
    });

    document.getElementById('summarizeBtn').addEventListener('click', summarizeText);
    document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
});


async function summarizeText() {
    try {
        const [tab] = await chrome.tabs.query({ active:true, currentWindow: true});
        const [{ result }] = await chrome.scripting.executeScript({
            target: {tabId: tab.id},
            function: () => document.body.innerText
        });

        if (!result) {
            showResult('Please select some text first');
            return;
        }

        const response = await fetch('http://localhost:8080/api/summarize', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content: result, operation: 'summarize'})
        });

        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }

        const summarizeObj = await response.json();
        showResult(summarizeObj.summary.replace(/\n/g,'<br>'));

    } catch (error) {
        showResult('Error: ' + error.message);
    }
}



function showResult(content) {
    document.getElementById('results').innerHTML = `<div class="result-item"><div class="result-content">${content}</div></div>`;
}