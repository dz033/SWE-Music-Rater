import './new.css';

export default function NewPage() {
    return (
        <div className="new-page"> 
        <div className="left-column">
                <h1>New Releases</h1>
                <p>Put albums whose release data less 1 month</p>
            </div>
            <div className="right-column">
                <h2>Upcoming</h2>
                <p>Stay tuned for these upcoming albums!</p>
            </div>
        </div>
    );
}