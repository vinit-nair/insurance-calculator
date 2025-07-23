import React, { useState, useEffect } from 'react';
import './AchievementBadge.css';

interface AchievementBadgeProps {
  id: string;
  title: string;
  description: string;
  icon: string;
  points: number;
  isUnlocked: boolean;
  isNew?: boolean;
  size?: 'small' | 'medium' | 'large';
  onClick?: () => void;
}

const AchievementBadge: React.FC<AchievementBadgeProps> = ({
  id,
  title,
  description,
  icon,
  points,
  isUnlocked,
  isNew = false,
  size = 'medium',
  onClick
}) => {
  const [showAnimation, setShowAnimation] = useState(false);

  useEffect(() => {
    if (isNew && isUnlocked) {
      setShowAnimation(true);
      const timer = setTimeout(() => setShowAnimation(false), 3000);
      return () => clearTimeout(timer);
    }
  }, [isNew, isUnlocked]);

  const handleClick = () => {
    if (onClick) {
      onClick();
    }
  };

  return (
    <div 
      className={`achievement-badge ${size} ${isUnlocked ? 'unlocked' : 'locked'} ${showAnimation ? 'animate' : ''}`}
      onClick={handleClick}
      title={isUnlocked ? `${title} - ${description}` : 'Complete this section to unlock'}
    >
      <div className="badge-icon">
        {isUnlocked ? (
          <span className="icon">{icon}</span>
        ) : (
          <span className="icon locked">🔒</span>
        )}
      </div>
      
      <div className="badge-content">
        <h4 className="badge-title">{title}</h4>
        <p className="badge-description">{description}</p>
        <div className="badge-points">
          {isUnlocked ? (
            <span className="points-earned">+{points} points</span>
          ) : (
            <span className="points-potential">{points} points</span>
          )}
        </div>
      </div>

      {showAnimation && (
        <div className="unlock-animation">
          <div className="celebration-burst">
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
          </div>
          <div className="unlock-text">Unlocked!</div>
        </div>
      )}
    </div>
  );
};

export default AchievementBadge; 