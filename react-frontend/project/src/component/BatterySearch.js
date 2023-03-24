import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import styles from '../css/BatterySearch.module.css';

function BatterySearch() {
  const navigate = useNavigate();

  const [startPostcode, setStartPostcode] = useState('');
  const [endPostcode, setEndPostcode] = useState('');
  const [batteryDetails, setBatteryDetails] = useState(null);

  const handleBatteryForm = () => {
    navigate('/');
  };

  const handleSearch = () => {
    fetch(
      `http://localhost:8080/batteries/?startPostcode=${startPostcode}&endPostcode=${endPostcode}`
    )
      .then((response) => response.json())
      .then((data) => {
        setBatteryDetails(data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className={styles.container}>
      <div className={styles.buttonContainer}>
        <button
          className={styles.button}
          type='button'
          onClick={handleBatteryForm}
        >
          Battery Form
        </button>
      </div>
      <h2 className={styles.title}>Search Batteries by Postcode Range</h2>
      <div className={styles.form}>
        <label htmlFor='startPostcode' className={styles.label}>
          Start Postcode:
          <input
            type='text'
            id='startPostcode'
            value={startPostcode}
            onChange={(e) => setStartPostcode(e.target.value)}
            className={styles.input}
          />
        </label>
      </div>
      <div className={styles.form}>
        <label htmlFor='endPostcode' className={styles.label}>
          End Postcode:
          <input
            type='text'
            id='endPostcode'
            value={endPostcode}
            onChange={(e) => setEndPostcode(e.target.value)}
            className={styles.input}
          />
        </label>
      </div>
      <button type='submit' onClick={handleSearch} className={styles.button}>
        Search
      </button>
      {batteryDetails && (
        <div className={styles.results}>
          <h3 className={styles.resultTitle}>Results:</h3>
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Count</th>
                <th>Battery Names</th>
                <th>Total Capacity</th>
                <th>Average Capacity</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{batteryDetails.count}</td>
                <td>{batteryDetails.batteryNames.join(', ')}</td>
                <td>{batteryDetails.totalCapacity}</td>
                <td>{batteryDetails.averageCapacity}</td>
              </tr>
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default BatterySearch;
