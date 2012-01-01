/**
 * Goertzel function 
 * 
 * see http://en.wikipedia.org/wiki/Goertzel_algorithm
 * danja 2012-01-01
 *
 * @param {Number} targetFrequency  frequency to look for in the signal
 * @param {Number} sampleRate The sample rate of the signal
 * @param {Number} buffer sample buffer
 *
 * @return power of given frequency in input
 */

Goertzel.power = function(targetFrequency, sampleRate, buffer) {

   var sumPrevious = 0;
   var sumPrevious2 = 0;
   var normalizedFrequency = targetFrequency / sampleRate;
   var coefficient = 2 * Math.cos(DSP.TWO_PI * normalizedFrequency);
   var sum;

   for ( var i = 0; i < buffer.length; i++ ) {
      sum = buffer[i] + coefficient * sumPrevious - sumPrevious2;
      sumPrevious2 = sumPrevious;
      sumPrevious = sum;
   }
   return sumPrevious2 * sumPrevious2 + sumPrevious * sumPrevious - coefficient * sumPrevious * sumPrevious2;
};


