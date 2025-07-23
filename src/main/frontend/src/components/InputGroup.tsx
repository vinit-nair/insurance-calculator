import React, { useState } from 'react';

interface InputGroupProps {
  label: string;
  name: string;
  type: string;
  value: string;
  onChange: (name: string, value: string) => void;
  onBlur: (name: string, value: string) => void;
  error?: string;
  tooltip?: string;
  required?: boolean;
  min?: number;
  max?: number;
  step?: number;
  placeholder?: string;
  prefix?: string;
}

const InputGroup: React.FC<InputGroupProps> = ({
  label,
  name,
  type,
  value,
  onChange,
  onBlur,
  error,
  tooltip,
  required = false,
  min,
  max,
  step,
  placeholder,
  prefix
}) => {
  const [showTooltip, setShowTooltip] = useState(false);

  return (
    <div className="input-group">
      <label htmlFor={name}>
        {label}
        {required && <span className="required">*</span>}
      </label>
      
      <div className="input-wrapper">
        {prefix && <span className="input-prefix">{prefix}</span>}
        <input
          id={name}
          name={name}
          type={type}
          value={value}
          onChange={(e) => onChange(name, e.target.value)}
          onBlur={(e) => onBlur(name, e.target.value)}
          className={error ? 'error' : ''}
          required={required}
          min={min}
          max={max}
          step={step}
          placeholder={placeholder}
          style={prefix ? { paddingLeft: '2.5rem' } : {}}
        />
        
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

export default InputGroup; 