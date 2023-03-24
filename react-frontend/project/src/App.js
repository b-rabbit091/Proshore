import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import BatteryForm from './component/BatterForm';
import BatterySearch from './component/BatterySearch';

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path='/' element={<BatteryForm />} />
        <Route path='/battery-search' element={<BatterySearch />} />
      </Routes>
    </Router>
  );
}

export default App;
