import React from 'react';

interface TooltipProps {
  content: string;
  show: boolean;
}

const Tooltip: React.FC<TooltipProps> = ({ content, show }) => {
  if (!show) return null;

  return (
    <div className="tooltip-popup show">
      {content}
    </div>
  );
};

export default Tooltip; 