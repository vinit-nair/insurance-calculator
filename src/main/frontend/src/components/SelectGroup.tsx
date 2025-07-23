import React, { useState } from 'react';

interface SelectOption {
  value: string;
  label: string;
}

interface SelectGroupProps {
  label: string;
  name: string;
  value: string;
  onChange: (name: string, value: string) => void;
  onBlur: (name: string, value: string) => void;
  error?: string;
  tooltip?: string;
  options: SelectOption[];
  required?: boolean;
}

const SelectGroup: React.FC<SelectGroupProps> = ({
  label,
  name,
  value,
  onChange,
  onBlur,
  error,
  tooltip,
  options,
  required
}) => {
  const [showTooltip, setShowTooltip] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    onChange(name, e.target.value);
  };

  const handleBlur = (e: React.FocusEvent<HTMLSelectElement>) => {
    onBlur(name, e.target.value);
  };

  return (
    <div className="input-group">
      <label htmlFor={name}>
        {label}
        {required && <span className="required">*</span>}
      </label>
      
      <div className="input-wrapper">
        <select
          id={name}
          name={name}
          value={value}
          onChange={handleChange}
          onBlur={handleBlur}
          required={required}
          className={error ? 'error' : ''}
        >
          {options.map(option => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
        
        {tooltip && (
          <span
            className="tooltip"
            onMouseEnter={() => setShowTooltip(true)}
            onMouseLeave={() => setShowTooltip(false)}
          >
            ℹ️
            {showTooltip && (
              <div className="tooltip-popup show">
                {tooltip}
              </div>
            )}
          </span>
        )}
      </div>
      
      {error && <div className="field-error">{error}</div>}
    </div>
  );
};

export default SelectGroup; 